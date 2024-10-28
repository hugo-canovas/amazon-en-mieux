package fr.projet_webservice.user.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet_webservice.user.exceptions.BadRequestException;
import fr.projet_webservice.user.models.User;
import fr.projet_webservice.user.services.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public Mono<User> findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @PostMapping
    public Mono<User> save(@RequestBody User user) {
        if (user.getId() != null) {
            throw new BadRequestException("Id must be null");
        }

        return userService.save(user);
    }

    @PutMapping("{id}")
    public Mono<User> update(@PathVariable String id, @RequestBody User user) {
        if (!id.equals(user.getId())) {
            throw new BadRequestException("ids differ in url and body");
        }

        return userService.update(user);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        userService.deleteById(id);
    }

}
