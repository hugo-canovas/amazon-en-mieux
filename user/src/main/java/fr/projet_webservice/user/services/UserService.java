package fr.projet_webservice.user.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.projet_webservice.user.exceptions.BadRequestException;
import fr.projet_webservice.user.exceptions.NotFoundException;
import fr.projet_webservice.user.models.User;
import fr.projet_webservice.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("No user with id : " + id + " exists")));
    }

    public Mono<User> save(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Something goes wrong", e);
        }
    }

    public Mono<User> update(User user) {
        try {
            findById(user.getId());
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Something goes wrong", e);
        }
    }

    public void deleteById(String id) {
        findById(id);
        userRepository.deleteById(id)
                .doOnSuccess(unused -> System.out.println("Deleted successfully"))
                .doOnError(error -> System.out.println("Error: " + error.getMessage()))
                .subscribe();
    }
}
