package com.server.newhopeserver;

import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Config {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @SuppressWarnings("null")
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @Bean
    public GlobalOpenApiCustomizer openApiCustomizer() {
        Info info = new Info();
        info.setTitle("Documentacion New hope");
        info.setDescription(
                "Aqui se encuentra la documentacion de un servidor que siguiendo el protocolo New Hope genrara una clave compartida entre cliente y servidor.\n\n"
                        + "El peticion consistira en un polinomio y un semilla, la respuesta consistira en otro polinomio y una pista que sera utilizada para encontrar la clave secreta compartida.\n\n"
                        + " Aqui solo se mustra la implementacion para JSON pero el servidor tambien tiene la capacidad recibir y resonder con el protocolo protobuf. \n El archivo .proto se puede encontrar [aqui](/msg.proto) ");
        return openApi -> {
            openApi.info(info);
        };
    }
}
