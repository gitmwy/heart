package com.ksh.heart.common.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ksh.heart.common.exception.HeartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 阿里云短信服务
 */
public class AliyunSmsService extends SmsService {

    private static Logger logger = LoggerFactory.getLogger(AliyunSmsService.class);

    private IAcsClient acsClient;

    public AliyunSmsService(SmsConfig config) {
        this.config = config;
        init();
    }

    private void init() {
        //设置超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "60000");
        System.setProperty("sun.net.client.defaultReadTimeout", "60000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile(config.getRegionId(), config.getAccessKeyId(), config.getAccessKeySecret());
        try {
            DefaultProfile.addEndpoint(config.getRegionId(), config.getRegionId(), config.getProduct(), config.getDomain());
        } catch (ClientException e) {
            logger.info(e.getMessage());
            throw new HeartException("初始化阿里短信失败");
        }
        acsClient = new DefaultAcsClient(profile);
    }

    /**
     * 发送短信验证码
     */
    public SmsResponse sendSms(String telephone) {
        //验证码code
        String code = getRandomNumCode(6);

        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(telephone); //待发送手机号
        request.setSignName(config.getSignName()); //短信签名-可在短信控制台中找到
        request.setTemplateCode(config.getTemplateCode()); //短信模板-可在短信控制台中找到
        request.setTemplateParam("{\"name\":\"" + telephone + "\", \"code\":\"" + code + "\"}"); //模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"

        //请求失败这里会抛ClientException异常
        try {
            SendSmsResponse response = acsClient.getAcsResponse(request);
            if (response.getCode() != null && response.getCode().equals("OK")) {
                Thread.sleep(3000L);
                return new SmsResponse().setCode(code).setContent(querySendDetails(response.getBizId(), telephone));
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 获取短信详细信息
     */
    public String querySendDetails(String bizId, String telephone) {

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setPhoneNumber(telephone); //手机号
        request.setBizId(bizId); //流水号
        request.setSendDate(new SimpleDateFormat("yyyyMMdd").format(new Date())); //发送日期 支持30天内记录查询，格式yyyyMMdd
        request.setPageSize(10L); //页大小
        request.setCurrentPage(1L); //当前页码从1开始

        //请求失败这里会抛ClientException异常
        try {
            QuerySendDetailsResponse response = acsClient.getAcsResponse(request);
            if(response.getCode() != null && response.getCode().equals("OK")){
                if(response.getSmsSendDetailDTOs().size() < 1){
                    return "";
                }
                return response.getSmsSendDetailDTOs().get(0).getContent();
            }
        } catch (ClientException e) {
            logger.info(e.getMessage());
        }
        return "";
    }
}