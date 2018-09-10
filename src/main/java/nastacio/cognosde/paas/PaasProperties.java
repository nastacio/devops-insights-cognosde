/********************************************************* {COPYRIGHT-TOP} ****
*  Copyright 2018 Denilson Nastacio
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
***************************************************************************** {COPYRIGHT-END} **/
package nastacio.cognosde.paas;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import nastacio.cognosde.exceptions.CognosException;

/**
 * Contains all runtime properties stored in the PaaS subsystem in association
 * with the application.
 * 
 * @author Denilson Nastacio
 * 
 */
public class PaasProperties {

    /**
     * Cloud Foundry VCAP environment variable for services
     */
    private static final String   VCAP_SERVICES    = "VCAP_SERVICES";

    /**
     * Cloud Foundry VCAP environment variable for applications
     */
    private static final String   VCAP_APPLICATION = "VCAP_APPLICATION";

    /**
     * CloudFoundry VCAP structure set during application startup.
     */
    private JsonObject            vcapJsonObject;

    /**
     * CloudFoundry VCAP services structure set during application startup.
     */
    private JsonObject            vcapServicesJsonObject;

    private DashDBService         dashService;

    private CognosEmbeddedService cognosService;

    /**
     * FQDN for the application.
     */
    private String                appUri;

    /**
     * Singleton instance.
     */
    private static PaasProperties instance;

    /**
     * Logging
     */
    private static final Logger   logger           = Logger.getLogger(PaasProperties.class.getName());

    /*
     * Public methods
     */

    /**
     * Singleton constructor.
     */
    public static synchronized PaasProperties getInstance() throws CognosException {
        if (null == instance) {
            instance = new PaasProperties();
            instance.readPaaSProperties();
        }
        return instance;
    }

    /*
     * Private methods
     */

    /**
     * Private constructor for singleton pattern
     */
    private PaasProperties() {
    }

    /**
     * @param vcapServicesJsonObject
     *            the vcapServicesJsonObject to set
     */
    public void setVcapServicesJsonObject(JsonObject vcapServicesJsonObject) {
        this.vcapServicesJsonObject = vcapServicesJsonObject;
    }

    /**
     * Retrieves the Cloud Foundry VCAP environment settings for this
     * application instance.
     */
    private void readPaaSProperties() throws CognosException {
        readPaaSApplicationProperties();
        readPaaSServiceProperties();
    }

    /**
     * @throws CognosException
     */
    private void readPaaSServiceProperties() throws CognosException {
        String vcapServices = System.getenv(VCAP_SERVICES);
        if (vcapServices == null) {
            String errMsg = MessageFormat.format("No {0} environment variable containing the service definitions.",
                    VCAP_SERVICES);
            throw new CognosException(errMsg);
        }

        StringReader jsonIoReader = new StringReader(vcapServices);
        try (JsonReader jsonReader = Json.createReader(jsonIoReader)) {
            ObjectMapper o = new ObjectMapper();
            o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            vcapServicesJsonObject = jsonReader.readObject();
            dashService = readPaaSDashServiceProperties(o);
            cognosService = readPaaSCognosServiceProperties(o);

        } catch (IOException e) {
            String errMsg = MessageFormat.format("Unable to read environment properties: {0}", e.getMessage());
            logger.log(Level.SEVERE, errMsg, e);
        }
    }

    /**
     * @param o
     * @throws CognosException
     * @throws IOException
     */
    private DashDBService readPaaSDashServiceProperties(ObjectMapper o) throws CognosException, IOException {
        final String dashDBServicesLabel = "dashDB";
        JsonArray dashDBServicesJson = vcapServicesJsonObject.getJsonArray(dashDBServicesLabel);
        if (dashDBServicesJson == null) {
            String errMsg = MessageFormat.format("No service group {0} in service definitions: {1}.",
                    dashDBServicesLabel, vcapServicesJsonObject.keySet().stream().collect(Collectors.joining(",")));
            throw new CognosException(errMsg);
        }

        JsonParser jp = o.getFactory().createParser(dashDBServicesJson.toString());
        DashDBService[] dashServices = jp.readValueAs(DashDBService[].class);
        if (dashServices.length == 0) {
            String errMsg = MessageFormat.format("No service definitions in group {0}.", dashDBServicesLabel);
            throw new CognosException(errMsg);
        }
        return dashServices[0];
    }

    /**
     * @param o
     * @return
     * @throws CognosException
     * @throws IOException
     */
    private CognosEmbeddedService readPaaSCognosServiceProperties(ObjectMapper o)
            throws CognosException, IOException {
        final String cognosServicesLabel = "dynamic-dashboard-embedded";
        JsonArray cognosServicesJson = vcapServicesJsonObject.getJsonArray(cognosServicesLabel);
        if (cognosServicesJson == null) {
            String errMsg = MessageFormat.format("No service group {0} in service definitions: {1}.",
                    cognosServicesLabel, vcapServicesJsonObject.keySet().stream().collect(Collectors.joining(",")));
            throw new CognosException(errMsg);
        }
        JsonParser jp = o.getFactory().createParser(cognosServicesJson.toString());
        CognosEmbeddedService[] cognosServices = jp.readValueAs(CognosEmbeddedService[].class);
        if (cognosServices.length == 0) {
            String errMsg = MessageFormat.format("No service definitions in group {0}.", cognosServicesLabel);
            throw new CognosException(errMsg);
        }
        return cognosServices[0];
    }

    /**
     * @throws CognosException
     */
    private void readPaaSApplicationProperties() throws CognosException {
        String vcapApp = System.getenv(VCAP_APPLICATION);
        if (vcapApp == null) {
            String errMsg = MessageFormat
                    .format("No {0} environment variable containing the application_uris attribute.", VCAP_APPLICATION);
            throw new CognosException(errMsg);
        }
        StringReader jsonIoReader = new StringReader(vcapApp);
        try (JsonReader jsonReader = Json.createReader(jsonIoReader)) {
            vcapJsonObject = jsonReader.readObject();
            JsonArray appUris = vcapJsonObject.getJsonArray("application_uris");
            appUri = appUris.getString(0);
            logger.info(() -> MessageFormat.format("Application URL is: {0}", appUri));
        }
    }

    /**
     * @return the dashService
     */
    public DashDBService getDashService() {
        return dashService;
    }

    /**
     * @return the cognosService
     */
    public CognosEmbeddedService getCognosService() {
        return cognosService;
    }

    /**
     * @return the appUri
     */
    public String getAppUri() {
        return appUri;
    }

    /**
     * @return the vcapJsonObject
     */
    public JsonObject getVcapJsonObject() {
        return vcapJsonObject;
    }

}
