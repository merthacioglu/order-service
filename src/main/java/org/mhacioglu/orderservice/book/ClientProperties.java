package org.mhacioglu.orderservice.book;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.net.URI;

@RefreshScope
@ConfigurationProperties(prefix = "polar")
public record ClientProperties (

    @NotNull
    URI catalogServiceUrl,

    String greeting
) {
    public String getGreeting() {
        return greeting;
    }
}
