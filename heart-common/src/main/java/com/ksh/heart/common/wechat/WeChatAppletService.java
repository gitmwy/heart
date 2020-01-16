package com.ksh.heart.common.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ksh.heart.common.support.HttpKit;
import com.ksh.heart.common.utils.ToolUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信业务
 */
public class WeChatAppletService extends WeChatService {

    public WeChatAppletService(WeChatConfig config) {
        this.config = config;
    }

    /**
     * 请求微信后台获取用户数据
     */
    public Map<String, String> JsCode2Session(String code, String encryptedData, String iv) throws Exception {
        //封装请求参数
        Map<String, String> param = new HashMap<>();
        param.put("appid", config.getAppId());
        param.put("secret", config.getSecret());
        param.put("grant_type", config.getGrant_type());
        param.put("js_code", code);

        // 发送请求
        String response = HttpKit.sendGet(config.getJsCode2Session(), param);
        // 解析返回信息
        JSONObject json = JSON.parseObject(response);
        String session_key = json.get("session_key").toString(); //获取会话密钥
        String openid = (String) json.get("openid"); //用户的唯一标识

        //对encryptedData加密数据进行AES解密
        String result = decrypt(encryptedData, session_key, iv);
        if(StringUtils.isNotBlank(result)) {
            JSONObject jsonObject = JSON.parseObject(result);
            Map<String, String> map = new HashMap<>();
            map.put("openid", openid);
            map.put("nickName", ToolUtil.valueOf(jsonObject.get("nickName")));
            map.put("avatarUrl", ToolUtil.valueOf(jsonObject.get("avatarUrl")));
            return map;
        }
        return null;
    }
}
