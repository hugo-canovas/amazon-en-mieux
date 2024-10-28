package fr.projet_webservice.user.services;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import fr.projet_webservice.consumer_order.Order;
import fr.projet_webservice.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerOrdersService implements Consumer<Order> {

    private final UserRepository userRepository;

    @Override
    public void accept(Order order) {
        System.out.println(order.getUser());
        userRepository.findById(order.getUser())
                .doOnNext(u -> u.getOrders().add(order.getId()))
                .flatMap(userRepository::save)
                .subscribe();
    }
}
