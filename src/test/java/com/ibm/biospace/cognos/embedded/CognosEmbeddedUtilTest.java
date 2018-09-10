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

import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 
 * @author Denilson Nastacio
 */
public class CognosEmbeddedUtilTest extends CognosEmbeddedUtil {

    @Test
    public void testSessionEncrypt() throws Exception {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("session-context.json")) {
            ObjectMapper o = new ObjectMapper();
            o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonParser jp = o.getFactory().createParser(is);
            CognosEmbeddedSession r = jp.readValueAs(CognosEmbeddedSession.class);
            CognosEmbeddedUtil ceu = new CognosEmbeddedUtil();
            SessionKey sessionKey = r.getKeys().get(0);
            String result = ceu.sessionEncrypt("aaaaaaaaaa", sessionKey);
            System.out.println(result);
        }
    }

}
