package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ✅ OS에 따라 다르게 설정
        String os = System.getProperty("os.name").toLowerCase();
        String uploadDir;

        if (os.contains("win")) { // Windows
            uploadDir = "file:./src/main/resources/static/uploads/";
        } else { // Linux (AWS 등)
            uploadDir = "file:/var/app/current/uploads/";
        }

        // 📌 Spring Boot에서 `/uploads/**` 요청을 해당 디렉토리에서 서빙하도록 설정
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadDir);
    }
}
