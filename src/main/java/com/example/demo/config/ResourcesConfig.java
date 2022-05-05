package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
    @Value("${config.upload}")
    private String directory;

    // Config đường dẫn ảnh
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("images/**")
                .addResourceLocations("file:/"+directory);
    }

    //Đẩy ảnh vào thư mục images
    public String uploadImage(MultipartFile file){
        String fileName = null;
        try {
            fileName = file.getOriginalFilename().replace(" ", "-");
            Path path = Paths.get(directory + fileName);
            Files.write(path, file.getBytes());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return fileName;
    }

    @Bean
    public FilterRegistrationBean<CartFilter> cartFilterFilterRegistrationBean(){
        FilterRegistrationBean<CartFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CartFilter());
        registrationBean.addUrlPatterns("/cart/*");
        return registrationBean;
    }
}
