package org.mhacioglu.orderservice.book.event;

public record OrderDistpatchedMessage(
        Long orderId
) {
}
