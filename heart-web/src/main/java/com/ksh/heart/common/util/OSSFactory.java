package com.ksh.heart.common.util;

import com.ksh.heart.common.cloud.AliyunCloudStorageService;
import com.ksh.heart.common.cloud.CloudStorageConfig;
import com.ksh.heart.common.cloud.CloudStorageService;
import com.ksh.heart.common.cloud.QcloudCloudStorageService;
import com.ksh.heart.common.cloud.QiniuCloudStorageService;
import com.ksh.heart.common.constant.ConfigConstant;
import com.ksh.heart.common.constant.Constant;
import com.ksh.heart.common.file.FtpConfig;
import com.ksh.heart.common.file.FtpUtil;
import com.ksh.heart.common.file.FtpService;
import com.ksh.heart.common.service.ISysConfigService;
import com.ksh.heart.common.sms.AliyunSmsService;
import com.ksh.heart.common.sms.SmsConfig;
import com.ksh.heart.common.sms.SmsService;
import com.ksh.heart.common.utils.SpringContextHolder;
import com.ksh.heart.common.wechat.WeChatConfig;
import com.ksh.heart.common.wechat.WeChatService;
import com.ksh.heart.common.wechat.WeChatAppletService;

/**
 * 获取配置信息Factory
 */
public final class OSSFactory {

    private static ISysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = SpringContextHolder.getBean("sysConfigService");
    }

    /**
     * 云存储
     */
    public static CloudStorageService buildCloud() {
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if (config.getType() == Constant.A_LI_YUN) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == Constant.Q_CLOUD) {
            return new QcloudCloudStorageService(config);
        }else if (config.getType() == Constant.QI_NIU) {
            return new QiniuCloudStorageService(config);
        }
        return null;
    }

    /**
     * ftp
     */
    public static FtpService buildFtp() {
        FtpConfig config = sysConfigService.getConfigObject(ConfigConstant.FTP_CONFIG_KEY, FtpConfig.class);
        return new FtpUtil(config);
    }

    /**
     * 云短信
     */
    public static SmsService buildSms() {
        SmsConfig config = sysConfigService.getConfigObject(ConfigConstant.SMS_CONFIG_KEY, SmsConfig.class);
        return new AliyunSmsService(config);
    }

    /**
     * 微信业务
     */
    public static WeChatService buildWeChat() {
        WeChatConfig config = sysConfigService.getConfigObject(ConfigConstant.WECHAT_CONFIG_KEY, WeChatConfig.class);
        return new WeChatAppletService(config);
    }
}
