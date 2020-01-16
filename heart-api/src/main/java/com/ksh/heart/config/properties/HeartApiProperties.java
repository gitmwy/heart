package com.ksh.heart.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = HeartApiProperties.HEART_REST_PREFIX)
public class HeartApiProperties {

    public static final String HEART_REST_PREFIX = "heart.api";

    private Boolean signOpen = true;

    private String secret;

    private Boolean captchaOpen = true;

    private Boolean redisOpen = true;

    private Boolean shiroRedis = true;

    public Boolean getSignOpen() {
        return signOpen;
    }

    public void setSignOpen(Boolean signOpen) {
        this.signOpen = signOpen;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Boolean getCaptchaOpen() {
        return captchaOpen;
    }

    public void setCaptchaOpen(Boolean captchaOpen) {
        this.captchaOpen = captchaOpen;
    }

    public Boolean getRedisOpen() {
        return redisOpen;
    }

    public void setRedisOpen(Boolean redisOpen) {
        this.redisOpen = redisOpen;
    }

    public Boolean getShiroRedis() {
        return shiroRedis;
    }

    public void setShiroRedis(Boolean shiroRedis) {
        this.shiroRedis = shiroRedis;
    }
}
