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

/**
 * 
 * 
 * @author Denilson Nastacio
 *
 */
@SuppressWarnings("serial")
public class ConfigException extends Exception {

    /**
     * 
     */
    public ConfigException() {
    }

    /**
     * @param message
     */
    public ConfigException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ConfigException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
