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
package nastacio.cognosde.util;

import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import nastacio.cognosde.util.CognosEmbeddedSession;
import nastacio.cognosde.util.CognosEmbeddedUtil;
import nastacio.cognosde.util.SessionKey;

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
