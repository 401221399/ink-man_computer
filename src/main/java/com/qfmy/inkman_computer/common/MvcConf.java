package com.qfmy.inkman_computer.common;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 WebMvcConfigurer：静态资源映射
 */
@Configuration
public class MvcConf implements WebMvcConfigurer {

    //这里需要配置,静态资源,加了shiro后，thymleaf
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}