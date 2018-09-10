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
package com.ibm.biospace.cognos.exceptions;

/**
 * Base exception for the whole application.
 * 
 * @author Denilson Nastacio
 */
@SuppressWarnings("serial")
public class CognosException extends Exception {

    /**
     * 
     */
    public CognosException() {
    }

    /**
     * @param message
     */
    public CognosException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public CognosException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public CognosException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CognosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
