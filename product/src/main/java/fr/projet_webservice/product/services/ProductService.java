package fr.projet_webservice.product.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.projet_webservice.product.exceptions.BadRequestException;
import fr.projet_webservice.product.exceptions.NotFoundException;
import fr.projet_webservice.product.models.Product;
import fr.projet_webservice.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    public Mono<Product> findById(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("No product with id : " + id + " exists")));
    }

    public Mono<Product> save(Product product) {
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Something goes wrong", e);
        }
    }

    public Mono<Product> update(Product product) {
        try {
            findById(product.getId());
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Something goes wrong", e);
        }
    }

    public void deleteById(String id) {
        findById(id);
        productRepository.deleteById(id)
                .doOnSuccess(unused -> System.out.println("Deleted successfully"))
                .doOnError(error -> System.out.println("Error: " + error.getMessage()))
                .subscribe();
    }
}
