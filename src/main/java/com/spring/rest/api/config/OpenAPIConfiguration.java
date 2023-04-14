package com.spring.rest.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_CONTACT_EMAIL;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_CONTACT_NAME;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_CONTACT_WEBSITE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_INFO_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_INFO_TERMS_OF_SERVICE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_INFO_TITLE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_INFO_VERSION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_LICENSE_NAME;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_LICENSE_WEBSITE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_SERVER_URL_DEV_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CONFIG_SERVER_URL_PROD_DESCRIPTION;


/**
 * Swagger configuration class.
 */
@Configuration
public class OpenAPIConfiguration {

    @Value("${openapi.dev-url}")
    private String devUrl;

    @Value("${openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription(CONFIG_SERVER_URL_DEV_DESCRIPTION);

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription(CONFIG_SERVER_URL_PROD_DESCRIPTION);

        Contact contact = new Contact();
        contact.setEmail(CONFIG_CONTACT_EMAIL);
        contact.setName(CONFIG_CONTACT_NAME);
        contact.setUrl(CONFIG_CONTACT_WEBSITE);

        License mitLicense = new License().name(CONFIG_LICENSE_NAME).url(CONFIG_LICENSE_WEBSITE);

        Info info = new Info()
                .title(CONFIG_INFO_TITLE)
                .version(CONFIG_INFO_VERSION)
                .contact(contact)
                .description(CONFIG_INFO_DESCRIPTION).termsOfService(CONFIG_INFO_TERMS_OF_SERVICE)
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
