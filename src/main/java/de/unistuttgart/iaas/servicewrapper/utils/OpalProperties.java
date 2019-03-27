/* Copyright 2017 Michael Hahn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.unistuttgart.iaas.servicewrapper.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by hahnml on 22.12.2017.
 */
public class OpalProperties extends Properties {

    private Logger logger = LoggerFactory.getLogger("de.unistuttgart.iaas.servicewrapper.utils.OpalProperties");

    private static final String PROPERTY_FILE_LOCATION = "/opalXYZR.properties";

    public static final String PROPERTY_TRADE_URL = "trade.url";
    public static final String PROPERTY_OPAL_EXEC_PATH = "opal.exec.path";
    public static final String PROPERTY_OPAL_DATA_PATH = "opal.data.path";

    public static final String ENV_VARIABLE_REGEX = "\\$\\{.+\\}";

    public OpalProperties() {
        this(null);
    }

    public OpalProperties(Properties defaults) {
        super(defaults);

        loadProperties();
    }

    public String getTraDEUrl() {
        return getProperty(PROPERTY_TRADE_URL, "http://localhost:8081/api");
    }

    public String getOpalExecutablePath() {
        return getProperty(PROPERTY_OPAL_EXEC_PATH, "/opalMC/bin");
    }

    public String getOpalDataPath() {
        return getProperty(PROPERTY_OPAL_DATA_PATH, "/opalMC/data");
    }

    private void loadProperties() {
        try {
            InputStream in = OpalProperties.class.getResourceAsStream(PROPERTY_FILE_LOCATION);

            if (in != null) {
                this.load(in);

                in.close();
            } else {
                logger.info("Loading properties from file was not successful. Using default properties instead.");
            }
        } catch (IOException e) {
            logger.info("Loading properties from file was not successful. Using default properties instead.");
        }
    }

    @Override
    public String getProperty(String key) {
        String result = super.getProperty(key);

        // Check if the value references an environment variable and
        // resolve it
        if (result != null && result.matches(ENV_VARIABLE_REGEX)) {
            result = System.getenv(result.substring(2, result.length() - 1));
        }

        return result;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        String result = getProperty(key);
        return (result == null) ? defaultValue : result;
    }
}
