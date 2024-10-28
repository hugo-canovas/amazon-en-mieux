package fr.projet_webservice.order.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.projet_webservice.order.exceptions.BadRequestException;
import fr.projet_webservice.order.exceptions.NotFoundException;
import fr.projet_webservice.order.models.Order;
import fr.projet_webservice.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    StreamBridge streamBridge;

    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }

    public Mono<Order> findById(String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("No user with id : " + id + " exists")));
    }

    public Mono<Order> save(Order order) {
        try {
            return orderRepository.save(order).doOnNext(
                    str -> streamBridge.send("order-out-0", fr.projet_webservice.consumer_order.Order.builder()
                            .id(order.getId())
                            .createdOn(order.getCreatedOn())
                            .user(order.getUser())
                            .products(order.getProducts())
                            .build()));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Something goes wrong", e);
        }
    }
}
