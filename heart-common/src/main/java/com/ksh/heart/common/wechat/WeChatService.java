package com.ksh.heart.common.wechat;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Map;

/**
 * 微信业务
 */
public abstract class WeChatService {

    /** 微信配置信息 */
    WeChatConfig config;

    /**
     * 请求微信后台获取用户数据
     */
    public abstract Map<String, String> JsCode2Session(String code, String encryptedData, String iv) throws Exception;

    /**
     * AES解密
     * @param encryptedData 密文
     * @param key 秘钥
     * @param iv 偏移量
     */
    public String decrypt(String encryptedData, String key, String iv) throws Exception{

        //被加密的数据
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        //加密秘钥
        byte[] keyByte = Base64.decodeBase64(key);
        //偏移量
        byte[] ivByte = Base64.decodeBase64(iv);

        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            return new String(resultByte, StandardCharsets.UTF_8);
        }
        return "";
    }
}
