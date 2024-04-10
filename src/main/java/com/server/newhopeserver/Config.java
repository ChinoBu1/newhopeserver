package com.server.newhopeserver;

import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import com.innogames.springfox_protobuf.ProtobufPropertiesModule;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Config {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ProtobufPropertiesModule());
        objectMapper.registerModule(new ProtobufModule());
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        return objectMapper;
    }

    @Bean
    public ModelResolver modelResolver(final ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    public GlobalOpenApiCustomizer openApiCustomizer() {
        Info info = new Info();
        info.setTitle("Documentacion New hope");
        info.setDescription(
                "Aqui se encuentra la documentacion de un servidor que siguiendo el protocolo New Hope genrara una clave compartida entre cliente y servidor.\n\n"
                        + " Aqui solo se mustra la implementacion para JSON pero el servidor tambien tiene la capacidad recibir y resonder con el protocolo protobuf. \n El archivo .proto se puede encontrar [aqui](/msg.proto) ");
        return openApi -> {
            openApi.info(info);
        };
    }
}
