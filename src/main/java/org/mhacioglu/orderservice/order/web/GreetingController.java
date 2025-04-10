package org.mhacioglu.orderservice.order.web;

import org.mhacioglu.orderservice.book.ClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    private final ClientProperties clientProperties;

    public GreetingController(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    @GetMapping
    public String getGreeting() {
        return clientProperties.getGreeting();
    }
}
