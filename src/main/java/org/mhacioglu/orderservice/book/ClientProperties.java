package org.mhacioglu.orderservice.book;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "polar")
public record ClientProperties (

    @NotNull
    URI catalogServiceUrl
) {}
