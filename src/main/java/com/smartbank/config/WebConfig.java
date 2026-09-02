package com.smartbank.config;

import java.util.List;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.smartbank")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();

        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");

        return resolver;
    }
    
    @Override
    public void extendMessageConverters(
            List<HttpMessageConverter<?>> converters) {
    	
    	converters.add(new MappingJackson2HttpMessageConverter());

        for (HttpMessageConverter<?> converter : converters) {
            System.out.println(
                "MESSAGE CONVERTER: " +
                converter.getClass().getName()
            );
        }
    }
    
    
    
    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }
}