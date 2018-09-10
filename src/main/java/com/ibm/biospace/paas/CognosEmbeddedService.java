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
package com.ibm.biospace.paas;

/**
 * @author Denilson Nastacio
 *
 */
public class CognosEmbeddedService {

    public String label;
    public String name;
    public String plan;
    public String provider;
    public String syslog_drain_url;
    public CognosEmbeddedCredentials credentials;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CognosEmbeddedService [label=" + label + ", name=" + name + ", plan=" + plan + ", provider=" + provider
                + ", syslog_drain_url=" + syslog_drain_url + ", creds=" + credentials.toString() + "]";
    }

}
