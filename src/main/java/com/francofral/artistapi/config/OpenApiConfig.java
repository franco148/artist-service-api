package com.francofral.artistapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:api-info.properties")
public class OpenApiConfig {

    // CONTACT INFO
    @Value("${contact.name}")
    private String contactName;

    @Value("${contact.url}")
    private String contactUrl;

    @Value("${contact.email}")
    private String contactEmail;

    // API INFO
    @Value("${api.info.title}")
    private String apiTitle;

    @Value("${api.info.description}")
    private String apiDescription;

    @Value("${api.info.version}")
    private String apiVersion;

    @Value("${api.info.terms-of-services}")
    private String apiTermsOfServices;

    @Value("${api.info.license}")
    private String apiLicense;

    @Value("${api.info.license-url}")
    private String apiLicenseUrl;

    @Value("${api.info.dev-url}")
    private String apiDevServerUrl;

    @Value("${api.info.docker-url}")
    private String apiDockerServerUrl;

    @Bean
    public OpenAPI api() {

        Server devServer = new Server();
        devServer.setUrl(apiDevServerUrl);
        devServer.setDescription("Server URL in Development environment");

        Server dockerServer = new Server();
        dockerServer.setUrl(apiDockerServerUrl);
        dockerServer.setDescription("Server URL in Docker environment");

        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(devServer, dockerServer));
    }

    private Info apiInfo() {

        Contact contact = new Contact();
        contact.setEmail(contactEmail);
        contact.setName(contactName);
        contact.setUrl(contactUrl);

        License apiLicenseInfo = new License()
                .name(apiLicense)
                .url(apiLicenseUrl);

        return new Info()
                .title(apiTitle)
                .description(apiDescription)
                .version(apiVersion)
                .contact(contact)
                .license(apiLicenseInfo);
    }
}
