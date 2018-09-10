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

import java.util.List;

/**
 * @author Denilson Nastacio
 *
 */
public class CognosEmbeddedSession {

	private String serviceInstanceId;
	private String sessionId;
	private String sessionCode;
	private long createTime;
	private long expiresIn;
	private List<SessionKey> keys;

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the sessionCode
	 */
	public String getSessionCode() {
		return sessionCode;
	}

	/**
	 * @param sessionCode
	 *            the sessionCode to set
	 */
	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}

	/**
	 * @return the keys
	 */
	public List<SessionKey> getKeys() {
		return keys;
	}

	/**
	 * @param keys
	 *            the keys to set
	 */
	public void setKeys(List<SessionKey> keys) {
		this.keys = keys;
	}

	/**
	 * @return the createTime
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the expiresIn
	 */
	public long getExpiresIn() {
		return expiresIn;
	}

	/**
	 * @param expiresIn
	 *            the expiresIn to set
	 */
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * @return the serviceInstanceId
	 */
	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	/**
	 * @param serviceInstanceId
	 *            the serviceInstanceId to set
	 */
	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

}
