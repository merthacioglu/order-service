package org.mhacioglu.orderservice.order.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("orders")
public record Order(

        @Id
        Long id,

        String bookIsbn,
        String bookName,
        Double bookPrice,
        Integer quantity,
        OrderStatus status,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate,

        @Version
        int version

) {
    public static Order bulld(
            String bookIsbn,
            String bookName,
            Double bookPrice,
            Integer quantity,
            OrderStatus status
    ) {
        return new Order(
                null,
                bookIsbn,
                bookName,
                bookPrice,
                quantity,
                status,
                null,
                null,
                0
        );
    }

    public static Order buildRejectedOrder(String bookIsbn, int quantity) {
        return bulld(bookIsbn, null, 0.0, quantity, OrderStatus.REJECTED);
    }
}
