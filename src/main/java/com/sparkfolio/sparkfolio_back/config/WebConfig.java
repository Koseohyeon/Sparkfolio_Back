package com.sparkfolio.sparkfolio_back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.servlet.file.saveDir}")
    private String saveDir; // 파일 저장 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // '/files/' 경로로 요청이 들어오면 실제 파일 시스템의 saveDir 경로로 매핑
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + saveDir + "/");
    }
}