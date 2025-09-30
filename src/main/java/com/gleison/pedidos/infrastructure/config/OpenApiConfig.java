package com.gleison.pedidos.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Sistema de Pedidos - API de Portfólio")
                .description("API RESTful para gestão de pedidos e catálogo de produtos, desenvolvida como projeto de estudo e portfólio.")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Edilgleidson Oliveira Silva")
                        .email("gleisondev5690@gmail.com")
                        .url("https://github.com/GleisonDev"))
                .license(new License()
                        .name("Licença de Portfólio (Uso Pessoal)")
                        .url("https://github.com/GleisonDev/sistema-pedidos"));
    }

}