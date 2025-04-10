package org.mhacioglu.orderservice.book;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;


import java.net.URI;

@ConfigurationProperties(prefix = "polar")
public class ClientProperties {
    public ClientProperties() {
    }

    @NotNull
    private URI catalogServiceUrl;

    private String greeting;

    // Getters and setters
    public URI getCatalogServiceUrl() {
        return catalogServiceUrl;
    }

    public void setCatalogServiceUrl(URI catalogServiceUrl) {
        this.catalogServiceUrl = catalogServiceUrl;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}