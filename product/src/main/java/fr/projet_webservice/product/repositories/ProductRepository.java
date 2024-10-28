package fr.projet_webservice.product.repositories;

import org.springframework.stereotype.Repository;

import fr.projet_webservice.product.models.Product;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

}
