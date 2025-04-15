package org.mhacioglu.orderservice.order.domain;

import org.mhacioglu.orderservice.book.Book;
import org.mhacioglu.orderservice.book.BookClient;
import org.mhacioglu.orderservice.book.event.OrderAcceptedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final BookClient bookClient;
    private final StreamBridge streamBridge;

    public OrderService(OrderRepository orderRepository,
                        BookClient bookClient,
                        StreamBridge streamBridge) {
        this.orderRepository = orderRepository;
        this.bookClient = bookClient;
        this.streamBridge = streamBridge;
    }
    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Mono<Order> submitOrder(String isbn, int quantity) {
        return bookClient.getBookByIsbn(isbn)
                .map(book -> buildAcceptedOrder(book, quantity))
                .defaultIfEmpty(buildRejectedOrder(isbn, quantity))
                .flatMap(orderRepository::save)
                .doOnNext(this::publishOrderAcceptedEvent);
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.findById(orderId)
                .map(existingOrder -> new Order(
                        existingOrder.id(),
                        existingOrder.bookIsbn(),
                        existingOrder.bookName(),
                        existingOrder.bookPrice(),
                        existingOrder.quantity(),
                        status,
                        existingOrder.createdDate(),
                        existingOrder.lastModifiedDate(),
                        existingOrder.version()
                ))
                .flatMap(orderRepository::save)
                .subscribe();
    }

    public void publishOrderAcceptedEvent(Order order) {
        if (!order.status().equals(OrderStatus.ACCEPTED)) {
            return;
        }

        OrderAcceptedMessage orderAcceptedMessage = new OrderAcceptedMessage(order.id());
        log.info("Sending order accepted event with id: {}", order.id());
        var result = streamBridge.send("order-accepted", orderAcceptedMessage);
        log.info("Result of sending data for order with id {}: {}", order.id(), result);

    }

    public static Order buildRejectedOrder(String bookIsbn, int quantity) {
        return Order.bulld(bookIsbn, null, null, quantity, OrderStatus.REJECTED);
    }

    public static Order buildAcceptedOrder(Book book, int quantity) {
        return Order.bulld(book.isbn(), book.title() + " - " + book.author(),
                book.price(), quantity, OrderStatus.ACCEPTED);

    }
}
