/*
 * Copyright (C) 2009-2018 Hangzhou FanDianEr Technology Co., Ltd. All rights reserved
 */
package main.java.fan.cors.server.config;

import main.java.fan.cors.server.filter.MyCorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * GlobalConfig
 *
 * @author lanxiang
 * @since 2018-11-12
 */
@Configuration
public class GlobalConfig {

    /**
     * 1.通过设置自定义 CorsFilter 过滤器支持跨域
     *
     * @return
     */
    @Bean
    public MyCorsFilter addCorsFilter() {
        return new MyCorsFilter();
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        // 设置支持带 Cookie 的跨域请求
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        // 设置非简单请求的预检命令缓存时间，单位 's'
        corsConfiguration.setMaxAge(1728000L);
        return corsConfiguration;
    }

    /**
     * 2.自定义 CORS 配置信息，并注入到 CorsFilter 过滤器中
     *
     * @return
     */
//    @Bean
    private CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
