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
 * @author Denilson Nastacio
 *
 */
public class SessionKey {

    private String kty;
    private String use;
    private String kid;
    private String alg;
    private String n;
    private String e;

    /**
     * @return the kty
     */
    public String getKty() {
        return kty;
    }
    /**
     * @param kty the kty to set
     */
    public void setKty(String kty) {
        this.kty = kty;
    }
    /**
     * @return the use
     */
    public String getUse() {
        return use;
    }
    /**
     * @param use the use to set
     */
    public void setUse(String use) {
        this.use = use;
    }
    /**
     * @return the kid
     */
    public String getKid() {
        return kid;
    }
    /**
     * @param kid the kid to set
     */
    public void setKid(String kid) {
        this.kid = kid;
    }
    /**
     * @return the alg
     */
    public String getAlg() {
        return alg;
    }
    /**
     * @param alg the alg to set
     */
    public void setAlg(String alg) {
        this.alg = alg;
    }
    /**
     * @return the n
     */
    public String getN() {
        return n;
    }
    /**
     * @param n the n to set
     */
    public void setN(String n) {
        this.n = n;
    }

    /**
     * @return the e
     */
    public String getE() {
        return e;
    }

    /**
     * @param e
     *            the e to set
     */
    public void setE(String e) {
        this.e = e;
    }
}
