package org.mhacioglu.orderservice.book.event;

public record OrderAcceptedMessage(
        Long orderId
) {
}
