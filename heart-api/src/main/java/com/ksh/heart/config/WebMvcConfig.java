package com.ksh.heart.config;

import com.ksh.heart.common.config.DefaultFastjsonConfig;
import com.ksh.heart.common.sign.converter.WithSignMessageConverter;
import com.ksh.heart.common.sign.security.DataSecurityAction;
import com.ksh.heart.common.sign.security.impl.Base64SecurityAction;
import com.ksh.heart.config.properties.HeartApiProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    @ConditionalOnProperty(prefix = HeartApiProperties.HEART_REST_PREFIX, name = "sign-open", havingValue = "true", matchIfMissing = true)
    public WithSignMessageConverter withSignMessageConverter() {
        WithSignMessageConverter withSignMessageConverter = new WithSignMessageConverter();
        DefaultFastjsonConfig defaultFastjsonConfig = new DefaultFastjsonConfig();
        withSignMessageConverter.setFastJsonConfig(defaultFastjsonConfig.fastjsonConfig());
        withSignMessageConverter.setSupportedMediaTypes(defaultFastjsonConfig.getSupportedMediaType());
        return withSignMessageConverter;
    }

    @Bean
    public DataSecurityAction dataSecurityAction() {
        return new Base64SecurityAction();
    }
}