package com.hook.restjwtproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ConfigSwagger {

    @Value("${hookTen.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI setOpenApi() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Develop environment");
        Contact contact = new Contact();
        contact.setEmail("hook@gmail.com");
        contact.setName("HookTen");
        contact.setUrl("https://someUrl.com");
        License licence = new License()
                .name("MIT Licence")
                .url("https://choosealicence.com/licence/mit/");
        Info info = new Info()
                .title("Rest Test API")
                .version("1.0")
                .contact(contact)
                .description("This API provides endpoints for management database of books and users, protected via jwt-token")
                .license(licence);

        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(info)
                .servers(List.of(devServer));
    }

}
