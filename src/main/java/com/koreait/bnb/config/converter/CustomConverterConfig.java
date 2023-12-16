package com.koreait.bnb.config.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomConverterConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSocialEnumConverter());
        registry.addConverter(new StringToUserRoleEnumConverter());
        WebMvcConfigurer.super.addFormatters(registry);
    }
}
