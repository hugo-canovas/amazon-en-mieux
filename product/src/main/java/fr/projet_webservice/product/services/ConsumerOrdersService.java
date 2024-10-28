package fr.projet_webservice.product.services;

import org.springframework.stereotype.Service;

import java.util.function.Consumer;

import fr.projet_webservice.consumer_order.Order;
import fr.projet_webservice.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerOrdersService implements Consumer<Order> {

    private final ProductRepository productRepository;

    @Override
    public void accept(Order order) {
        System.out.println(order);
        productRepository.findAllById(order.getProducts())
                .doOnNext(product -> product.getOrders().add(order.getId()))
                .collectList()
                .flatMapMany(productRepository::saveAll)
                .subscribe();
    }

}
