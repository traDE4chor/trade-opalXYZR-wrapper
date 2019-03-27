/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.unistuttgart.iaas.servicewrapper.opalxyzr.impl;

import de.uni_stuttgart.iaas.servicewrapper.opalxyzr.*;
import de.unistuttgart.iaas.servicewrapper.process.ProcessManager;
import de.unistuttgart.iaas.servicewrapper.utils.OpalProperties;
import io.swagger.trade.client.jersey.api.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;
import javax.xml.ws.BindingProvider;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebService(serviceName = "OpalXyzrService", endpointInterface = "de.uni_stuttgart.iaas.servicewrapper.opalxyzr.OpalXYZR",
        targetNamespace = "http://www.uni-stuttgart.de/iaas/serviceWrapper/opalXYZR/", portName = "OpalXyzrServicePort",
        name = "OpalXYZRServiceImpl")
public class OpalXYZRServiceImpl implements OpalXYZR {

    private Logger logger = LoggerFactory.getLogger("de.unistuttgart.iaas.servicewrapper.opalxyzr.impl.OpalXYZRServiceImpl");

    private ConcurrentHashMap<String, Integer> _simulationID2PrefixMap;

    private ApiClient _tradeApiClient;

    private ExecutorService _executor;

    public OpalXYZRServiceImpl() {
        _simulationID2PrefixMap = new ConcurrentHashMap<>();

        _executor = Executors.newCachedThreadPool();

        _tradeApiClient = new ApiClient();
        _tradeApiClient.setBasePath(new OpalProperties().getTraDEUrl());
    }

    @Override
    public RunOpalXyzrResponse runOpalXYZR(RunOpalXyzr parameters) {
        TSimulationIdentifier simulationID = parameters.getSimulationIdentifier();
        boolean started = false;
        String message = "";

        int index = parameters.getNumberOfSnapshotToProcess();
        int lx = parameters.getLx();
        int ly = parameters.getLy();
        int lz = parameters.getLz();
        int Nb = parameters.getNb();
        int Nc = parameters.getNc();
        int Nd = parameters.getNd();

        // Create a new ProcessManager for the given simulationID
        ProcessManager manager = new ProcessManager(simulationID, _tradeApiClient);
        // Calculate the next available prefix for running the simulation. The prefix is required to avoid file
        // collisions on the underlying OS file system between potentially concurrently running simulations.
        _simulationID2PrefixMap.put(simulationID.getKey(), manager.calcNextPrefix(_simulationID2PrefixMap.values()));

        int prefix = _simulationID2PrefixMap.get(simulationID.getKey());

        try {
            // Prepare the simulation for execution: download input files, create folders, etc.
            manager.prepareProcessExecution(prefix, index, parameters.getDataModelRef(), parameters
                    .getClustersDataElement(), parameters.getClusterPositionsDataElement());

            // Run the simulation in a non-blocking way in a separate thread
            Runnable r = () -> {
                try {
                    // Run the simulation
                    manager.startProcess("opalxyzrarg", String.format(ProcessManager.PREFIX_FORMAT, prefix), String
                            .format(ProcessManager.SUFFIX_FORMAT, index), lx, ly, lz, Nb, Nc, Nd);

                    // Clean up the simulation after execution: upload result files, delete folders, etc.
                    TResultDataList results = manager.postProcessingAndCleanUp(parameters.getResultMDataElement(), parameters
                            .getResultDisradDataElement(), parameters.getResultCPrecDataElement(), parameters.getResultXyzrDataElement());

                    try {
                        // Create a new callback client and trigger the callback
                        OpalXYZRCallback client = createCallbackClient(parameters.getReplyToCallbackAddress());

                        Callback callback = new Callback();
                        callback.setSimulationIdentifier(simulationID);
                        callback.setResultDataLinks(results);
                        callback.setFaultMessage(null);

                        client.callbackXYZR(callback);
                    } catch (Exception e) {
                        logger.error("Sending the results to the specified callback service caused an " +
                                "exception.", e);
                    }
                } catch (Exception e) {
                    try {
                        // Create a new callback client and trigger the callback
                        OpalXYZRCallback client = createCallbackClient(parameters.getReplyToCallbackAddress());

                        Callback callback = new Callback();
                        callback.setSimulationIdentifier(simulationID);
                        callback.setResultDataLinks(null);
                        callback.setFaultMessage(e.getMessage());

                        client.callbackXYZR(callback);
                    } catch (Exception ex) {
                        logger.error("Sending the results to the specified callback service caused an " +
                                "exception.", ex);
                    }
                } finally {
                    _simulationID2PrefixMap.remove(simulationID.getKey());
                }
            };

            _executor.execute(r);

            started = true;
        } catch (Exception e) {
            message = e.getMessage();
            started = false;

            logger.error("Preparation of service operation execution caused an exception.", e);
        }

        RunOpalXyzrResponse response = new RunOpalXyzrResponse();
        response.setSimulationID(simulationID.getValue());
        response.setMessage(message);
        response.setStarted(started);

        return response;
    }

    private OpalXYZRCallback createCallbackClient(String replyToCallbackAddress) {
        // We need to load the callback WSDL from the classpath
        URL wsdlLocation = null;
        try {
            wsdlLocation =
                    this.getClass().getResource("/OpalCallbackInterface.wsdl");
        } catch (Exception e) {
            logger.error("Loading the OpalCallbackInterface.wsdl from classpath caused an exception.", e);
        }

        // Create the client
        OpalXYZRCallbackService service = new OpalXYZRCallbackService(wsdlLocation);
        OpalXYZRCallback port = service.getOpalXYZRCallbackServicePort();

        // Change the endpoint address to the provided reply-to address
        BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getRequestContext()
                .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, replyToCallbackAddress);

        return port;
    }
}
