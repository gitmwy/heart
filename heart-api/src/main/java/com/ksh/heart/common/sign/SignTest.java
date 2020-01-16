package com.ksh.heart.common.sign;

import com.ksh.heart.common.sign.security.impl.Base64SecurityAction;
import com.ksh.heart.common.utils.MD5Util;

public class SignTest {

    public static void main(String[] args) {
        Base64SecurityAction base64SecurityAction = new Base64SecurityAction();

        String a = "{\n" +
                " \"username\": \"18247270927\",\n" +
                " \"loginType\": \"02\",\n" +
                " \"phoneCode\": \"213534\",\n" +
                "}";

//                {
//                    "object":"ewogInVzZXJuYW1lIjogIjEzMzI3NzM2MzI1IiwKICJsb2dpblR5cGUiOiAiMDEiLAogInBhc3N3b3JkIjogIjEyMzQ1NiIsCn0=",
//                    "sign":"987d56e08f2294213cd6034da1616b64"
//                }
//                {
//                    "object":"ewogInVzZXJuYW1lIjogIjE4ODUxMDA2MDYwIiwKICJsb2dpblR5cGUiOiAiMDEiLAogInBhc3N3b3JkIjogIjEyMzQ1NiIsCn0=",
//                    "sign":"76d107fb4f286d145dc060229f2f282e"
//                }
        String o = base64SecurityAction.doAction(a);
        System.out.println(o);
        String encrypt =  MD5Util.encrypt(o + "miyao");
        System.out.println(encrypt);
        String u = base64SecurityAction.unlock(o);
        System.out.println(u);
    }
}
