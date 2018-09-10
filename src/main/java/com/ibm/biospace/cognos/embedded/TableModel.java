/********************************************************* {COPYRIGHT-TOP} ***
* IBM Confidential
* OCO Source Materials
* WfG
*
* (C) Copyright IBM Corp. 2018  All Rights Reserved.
*
* The source code for this program is not published or otherwise  
* divested of its trade secrets, irrespective of what has been 
* deposited with the U.S. Copyright Office.
 ********************************************************* {COPYRIGHT-END} **/
package com.ibm.biospace.cognos.embedded;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.biospace.cognos.exceptions.CognosException;
import com.ibm.biospace.paas.DashDBCredentials;
import com.ibm.biospace.paas.PaasProperties;

/**
 * @author Denilson Nastacio
 *
 */
public class TableModel {

    /**
     * 
     */
    private static final String COM_IBM_DB2_JCC_DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";

    /**
     * 
     * @return
     * @throws CognosException
     */
    public String getSchema() throws CognosException {

        String result = null;

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode topLevel = mapper.createObjectNode();
        topLevel.put("xsd", "https://ibm.com/daas/module/1.0/module.xsd");
        topLevel.put("label", "Analysis Module");
        topLevel.put("identifier", "analysis-v.1.0.0a");

        String tableName = "DIMENSION_ANALYSIS";

        ObjectNode sourceO = topLevel.putObject("source");
        try (Connection dbc = getWfgDbConnection(sourceO)) {
            ObjectNode table = topLevel.putObject("table");
            queryDataModel(dbc, tableName, table);
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(topLevel);
        } catch (JsonProcessingException e) {
            String errMsg = MessageFormat.format("Unable to serialize the tqble schema for table {0}, due to {1}",
                    tableName, e.getMessage());
            throw new CognosException(errMsg, e);
        } catch (SQLException e) {
            String errMsg = MessageFormat.format("Unable to query the table definition for table {0}, due to {1}",
                    tableName, e.getMessage());
            throw new CognosException(errMsg, e);
        }

        return result;
    }

    /**
     * 
     * @param conn
     * @param table
     * @throws SQLException
     */
    private ObjectNode queryDataModel(Connection conn, String table, ObjectNode tableO) throws CognosException {

        ObjectMapper mapper = new ObjectMapper();

        tableO.put("name", table);
        tableO.put("description", "some description");
        ArrayNode arrayNode = tableO.putArray("column");
        try (PreparedStatement stmt = conn
                .prepareStatement("SELECT * " + "FROM " + table + " FETCH FIRST 1 ROWS ONLY")) {
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i < metaData.getColumnCount(); i++) {

                    String dataType = "";
                    int columnSize = metaData.getColumnDisplaySize(i);
                    switch (metaData.getColumnTypeName(i)) {
                    case "VARCHAR":
                        dataType = "NVARCHAR(" + columnSize + ")";
                        break;
                    case "CHAR":
                        dataType = "CHAR(" + columnSize + ")";
                        break;
                    case "DECIMAL":
                        dataType = "DECIMAL(" + metaData.getPrecision(i) + "," + metaData.getScale(i) + ")";
                        break;
                    default:
                        dataType = metaData.getColumnTypeName(i);
                    }

                    ObjectNode co = mapper.createObjectNode();
                    co.put("name", metaData.getColumnLabel(i));
                    co.put("description", metaData.getColumnTypeName(i));
                    co.put("datatype", dataType);
                    co.put("nullable", false);
                    co.put("label", metaData.getColumnLabel(i));
                    co.put("usage", "attribute");
                    co.put("regularAggregate", "countDistinct");
                    arrayNode.add(co);
                }
            }
        } catch (SQLException e) {
            String errMsg = MessageFormat.format("Unable to query the table definition for table {0}, due to {1}",
                    table, e.getMessage());
            throw new CognosException(errMsg, e);
        }

        return tableO;
    }

    /**
     * 
     * @return
     * @throws ConfigException
     */
    private Connection getWfgDbConnection(ObjectNode sourceO) throws CognosException {
        Connection conn = null;

        String dbUrl = "";
        try {
            Class.forName(COM_IBM_DB2_JCC_DB2_DRIVER);
            String dsType = "db2";
            String dbName = "BLUDB";
            final DashDBCredentials dashCredentials = PaasProperties.getInstance().getDashService().credentials;
            String dbHost = dashCredentials.hostname;
            int dbPort = dashCredentials.port;

            dbUrl = "jdbc:" + dsType + "://" + dbHost + ":" + dbPort + "/" + dbName + ":" + "sslConnection=false;";

            String user = dashCredentials.username;
            String password = dashCredentials.password;
            conn = DriverManager.getConnection(dbUrl, user, password);

            sourceO.put("id", "StringID");
            sourceO.put("user", user);
            sourceO.put("password", password);
            ObjectNode jdbcO = sourceO.putObject("jdbc");
            jdbcO.put("jdbcUrl", dbUrl);
            jdbcO.put("driverClassName", COM_IBM_DB2_JCC_DB2_DRIVER);
            jdbcO.put("connectionProperties", "");
        } catch (SQLException e) {
            String errMsg = MessageFormat.format(
                    "Unable to establish connection to WfG configuration database [{0}] due to {1}", dbUrl,
                    e.getMessage());
            throw new CognosException(errMsg, e);
        } catch (ClassNotFoundException e) {
            String errMsg = MessageFormat.format("Could not locate JDBC driver for [{0}]", COM_IBM_DB2_JCC_DB2_DRIVER);
            throw new CognosException(errMsg, e);
        }

        return conn;
    }
}
