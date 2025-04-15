package org.mhacioglu.orderservice.book.event;

import org.mhacioglu.orderservice.order.domain.OrderService;
import org.mhacioglu.orderservice.order.domain.OrderStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;


@Configuration
public class OrderFunctions {
    private static final Logger log = LoggerFactory.getLogger(OrderFunctions.class);

    @Bean
    public Consumer<OrderDistpatchedMessage> dispatchOrder(OrderService orderService) {
        return orderDispatchedMessage -> {
            log.info("The order with id {} has been dispatched", orderDispatchedMessage.orderId());
            orderService.updateOrderStatus(orderDispatchedMessage.orderId(), OrderStatus.DISPATCHED);
        };
    }

}
