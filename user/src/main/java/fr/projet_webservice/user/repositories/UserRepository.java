package fr.projet_webservice.user.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import fr.projet_webservice.user.models.User;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

}
