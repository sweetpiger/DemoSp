package com.example.demo.enlys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许前端的源（IDEA的63342端口）
        config.addAllowedOrigin("http://localhost:63342");
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 允许所有HTTP方法（GET/POST等）
        config.addAllowedMethod("*");
        // 允许携带Cookie（若有需要）
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有接口生效
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
