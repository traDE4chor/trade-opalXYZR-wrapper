package de.unistuttgart.iaas.servicewrapper.process;

import de.uni_stuttgart.iaas.servicewrapper.opalxyzr.*;
import de.unistuttgart.iaas.servicewrapper.utils.OpalProperties;
import io.swagger.trade.client.jersey.api.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trade.core.client.TraDEManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class ProcessManager {

    private Logger logger = LoggerFactory.getLogger("de.unistuttgart.iaas.servicewrapper.process.ProcessManager");

    private OpalProperties _props;

    private TSimulationIdentifier _simulationID;

    private HashMap<String, String> _dataElement2FileName;

    private TraDEManager _tradeManager;

    private Path _opalDataFolder;
    private Integer _prefix;
    private TDataModelReference _modelReference;
    private int _index;

    public static final String LOG_FILE = "run.log";
    public static final String ERROR_FILE = "error.log";

    public static final String PREFIX_FORMAT = "%03d";
    public static final String SUFFIX_FORMAT = "%04d";

    public ProcessManager(TSimulationIdentifier simulationID, ApiClient tradeApiClient) {
        _simulationID = simulationID;

        _props = new OpalProperties();

        _dataElement2FileName = new HashMap<>();

        _tradeManager = new TraDEManager(tradeApiClient, simulationID.getKey(), simulationID.getValue());

        // Create all required mappings. The data element names have to be specified accordingly in the related Data
        // Model at the TraDE Middleware. The values of the map contain the file name patterns of the required files
        // with prefix (%d3 - 3 digits before) and suffix (%d4 - four digits after) the actual file names.
        _dataElement2FileName.put("clusters[]", PREFIX_FORMAT + "clusidi" + SUFFIX_FORMAT + ".dat");
        _dataElement2FileName.put("posSizes[]", PREFIX_FORMAT + "cluspos" + SUFFIX_FORMAT + ".dat");
        _dataElement2FileName.put("cprec[]", PREFIX_FORMAT + "cprec" + SUFFIX_FORMAT + ".dat");
        _dataElement2FileName.put("disrad[]", PREFIX_FORMAT + "disrad" + SUFFIX_FORMAT + ".dat");
        _dataElement2FileName.put("m[]", PREFIX_FORMAT + "m" + SUFFIX_FORMAT + ".dat");
        _dataElement2FileName.put("xyzr[]", PREFIX_FORMAT + "xyzr" + SUFFIX_FORMAT + ".dat");
    }

    public void prepareProcessExecution(int prefix, int index, TDataModelReference modelReference,
                                        TDataElementRef...
                                                dataElements) throws Exception {
        // Create a new folder based on the _simulationID and the prefix
        _opalDataFolder = Paths.get(_props.getOpalDataPath(), _simulationID.getValue(), String.format(ProcessManager.PREFIX_FORMAT, prefix));
        _prefix = prefix;
        _modelReference = modelReference;
        _index = index;

        if (!Files.exists(_opalDataFolder)) {
            Files.createDirectories(_opalDataFolder);
        }

        // Download the required data element values from the TraDE Middleware
        downloadInputs(dataElements);
    }

    private void downloadInputs(TDataElementRef... dataElements) throws Exception {
        // Loop over all required input data elements and download the referenced data
        for (TDataElementRef elementRef : dataElements) {

            boolean isCollection = elementRef.isIsCollectionDataElement() != null ? elementRef
                    .isIsCollectionDataElement() : false;

            // Special handling for multi-value data elements
            if (isCollection) {
                byte[] data = _tradeManager.pull(_modelReference.getNamespaceURI(), _modelReference.getLocalName
                        (), elementRef.getDataObjectName(), elementRef.getDataElementName(), _index);

                if (data != null) {
                    String fileNameTemplate = _dataElement2FileName.get(elementRef.getDataElementName());

                    // Resolve the path for the input file based on the _dataElement2FileName map and the specified prefix
                    Path filePath = _opalDataFolder.resolve(String.format(fileNameTemplate, _prefix, _index));

                    Files.write(filePath, data, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                }
            } else {
                byte[] data = _tradeManager.pull(_modelReference.getNamespaceURI(), _modelReference.getLocalName
                        (), elementRef.getDataObjectName(), elementRef.getDataElementName(), null);

                if (data != null) {
                    String fileNameTemplate = _dataElement2FileName.get(elementRef.getDataElementName());

                    // Resolve the path for the input file based on the _dataElement2FileName map and the specified prefix
                    Path filePath = _opalDataFolder.resolve(String.format(fileNameTemplate, _prefix));

                    Files.write(filePath, data, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                }
            }
        }
    }

    public void startProcess(String executableFileName, Object... parameters) throws Exception {
        try {
            // Check if the underlying OS is windows or not
            boolean isWindows = System.getProperty("os.name")
                    .toLowerCase().startsWith("windows");

            List<String> command = new ArrayList<>();

            ProcessBuilder builder = new ProcessBuilder();
            if (isWindows) {
                command.add("cmd.exe");
                command.add("/c");
            } else {
                command.add("sh");
                command.add("-c");
            }

            String execCommand = _props.getOpalExecutablePath() + File.separator + executableFileName;

            if (isWindows) {
                command.add(execCommand);

                for (Object param : parameters) {
                    command.add(param.toString());
                }
            } else {
                // We have to wrap the executable command and all it's parameters into one argument since "sh -c"
                // on Linux interprets the first argument as string, else the arguments are not passed to the
                // invoked script or executable
                StringBuilder args = new StringBuilder();
                for (Object param : parameters) {
                    args.append(" ");
                    args.append(param.toString());
                }

                // Create the final command by concatenating the executable name and all arguments into one string
                command.add(execCommand + args.toString());
            }

            builder.command(command);
            builder.directory(_opalDataFolder.toFile());
            builder.redirectOutput(new File(_opalDataFolder.toFile(), LOG_FILE));
            builder.redirectError(new File(_opalDataFolder.toFile(), ERROR_FILE));

            Process process = builder.start();

            int exitCode = process.waitFor();
        } catch (Exception e) {
            logger.error("Running the executable '" + executableFileName + "' caused an exception.", e);

            throw e;
        }
    }

    private TResultDataList uploadResults(TDataElementRef... dataElements) {
        TResultDataList resultDataList = new TResultDataList();

        // Loop over all requested output data elements and upload the referenced data to the TraDE Middleware
        for (TDataElementRef elementRef : dataElements) {

            boolean isCollection = elementRef.isIsCollectionDataElement() != null ? elementRef
                    .isIsCollectionDataElement() : false;

            // Special handling for snapshots (multi-value data elements
            if (isCollection) {
                String fileNameTemplate = _dataElement2FileName.get(elementRef.getDataElementName());

                // Resolve the path for the result file based on the _dataElement2FileName map and the specified prefix
                Path filePath = _opalDataFolder.resolve(String.format(fileNameTemplate, _prefix, _index));

                byte[] data = null;

                try {
                    if (Files.exists(filePath)) {
                        data = Files.readAllBytes(filePath);
                    }
                } catch (IOException e) {
                    logger.error("Reading data from result file '" + filePath.toString() + "' caused an exception", e);
                }

                if (data != null) {
                    String dataValueLink = _tradeManager.push(_modelReference.getNamespaceURI(), _modelReference.getLocalName
                            (), elementRef.getDataObjectName(), elementRef.getDataElementName(), isCollection, data,
                            getContentType(fileNameTemplate), "opalXYZR-WebService-Wrapper#", _index);

                    // Collect the result data and add it to the result list
                    TResultRef resultRef = new TResultRef();
                    resultRef.setDataObjectName(elementRef.getDataObjectName());
                    resultRef.setDataElementName(elementRef.getDataElementName());
                    resultRef.setLinkToDataValue(dataValueLink);
                    resultRef.setDirectLinkToData(dataValueLink + "/data");

                    resultDataList.getResult().add(resultRef);
                }
            } else {
                String fileNameTemplate = _dataElement2FileName.get(elementRef.getDataElementName());

                // Resolve the path for the result file based on the _dataElement2FileName map and the specified prefix
                Path filePath = _opalDataFolder.resolve(String.format(fileNameTemplate, _prefix));

                byte[] data = null;

                try {
                    if (Files.exists(filePath)) {
                        data = Files.readAllBytes(filePath);
                    }
                } catch (IOException e) {
                    logger.error("Reading data from result file '" + filePath.toString() + "' caused an exception", e);
                }

                if (data != null) {
                    String dataValueLink = _tradeManager.push(_modelReference.getNamespaceURI(), _modelReference.getLocalName
                            (), elementRef.getDataObjectName(), elementRef.getDataElementName(), isCollection, data, getContentType(fileNameTemplate),"opalXYZR-WebService-Wrapper#", null);

                    // Collect the result data and add it to the result list
                    TResultRef resultRef = new TResultRef();
                    resultRef.setDataObjectName(elementRef.getDataObjectName());
                    resultRef.setDataElementName(elementRef.getDataElementName());
                    resultRef.setLinkToDataValue(dataValueLink);
                    resultRef.setDirectLinkToData(dataValueLink + "/data");

                    resultDataList.getResult().add(resultRef);
                }
            }
        }

        return resultDataList;
    }

    public TResultDataList postProcessingAndCleanUp(TDataElementRef... dataElements) throws Exception {
        TResultDataList results = uploadResults(dataElements);

        try {
            Files.walkFileTree(_opalDataFolder, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.error("Cleanup of data directory for simulation instance '" + _simulationID + "' caused an " +
                    "exception", e);

            throw e;
        }

        return results;
    }

    public int calcNextPrefix(Collection<Integer> prefixList) {
        // Get the highest prefix value currently in use
        OptionalInt lastPrefix = prefixList.stream().mapToInt(Integer::intValue).max();
        int prefix = lastPrefix.orElse(-1);

        // If we already reached the higher bound, we have to start from the beginning where previously used prefixes
        // should be free again.
        // NOTE: By default this will only allow to run 1000 simulation instances in parallel!
        if (prefix >= 999) {
            // We use -1 to end up with a prefix of 0 after incrementation
            prefix = -1;
        }

        // Increment the current prefix by one and return it
        prefix++;

        return prefix;
    }

    private String getContentType(String fileNameTemplate) {
        String result = "text/plain";

        if (fileNameTemplate.endsWith("mp4")) {
            result = "video/mp4";
        } else if (fileNameTemplate.endsWith("ps")) {
            result = "application/postscript";
        } else if (fileNameTemplate.endsWith("png")) {
            result = "image/png";
        }

        return result;
    }
}
