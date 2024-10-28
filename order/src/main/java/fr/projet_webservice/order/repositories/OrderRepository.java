package fr.projet_webservice.order.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import fr.projet_webservice.order.models.Order;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, String> {

}
