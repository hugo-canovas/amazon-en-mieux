package fr.projet_webservice.order.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet_webservice.order.exceptions.BadRequestException;
import fr.projet_webservice.order.models.Order;
import fr.projet_webservice.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Flux<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("{id}")
    public Mono<Order> findById(@PathVariable String id) {
        return orderService.findById(id);
    }

    @PostMapping
    public Mono<Order> save(@RequestBody Order order) {
        if (order.getId() != null) {
            throw new BadRequestException("Id must be null");
        }

        return orderService.save(order);
    }
}
