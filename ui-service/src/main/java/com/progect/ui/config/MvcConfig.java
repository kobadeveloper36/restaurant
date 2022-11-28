package com.progect.ui.config;

import com.progect.ui.UiApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final String uploadPath;

    public MvcConfig() {
        String location = UiApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(location);
        this.uploadPath = location.substring(0, location.indexOf("/BOOT-INF")) + "/uploads/img";
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://" + uploadPath + "/");
    }
}
