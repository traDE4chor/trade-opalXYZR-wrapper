package de.uni_stuttgart.iaas.servicewrapper.opalxyzr;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2019-03-27T13:49:09.380+01:00
 * Generated source version: 3.1.12
 * 
 */
@WebService(targetNamespace = "http://www.uni-stuttgart.de/iaas/serviceWrapper/opalXYZR/", name = "opalXYZR")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface OpalXYZR {

    @WebMethod(action = "http://www.uni-stuttgart.de/iaas/serviceWrapper/opalXYZR/runOpalXYZR")
    @WebResult(name = "runOpalXyzrResponse", targetNamespace = "http://www.uni-stuttgart.de/iaas/serviceWrapper/opalXYZR/", partName = "parameters")
    public RunOpalXyzrResponse runOpalXYZR(
        @WebParam(partName = "parameters", name = "runOpalXyzr", targetNamespace = "http://www.uni-stuttgart.de/iaas/serviceWrapper/opalXYZR/")
        RunOpalXyzr parameters
    );
}