package it.asap.authenticationservice.application.port.out;

import it.asap.authenticationservice.domain.model.User;

public interface UserRepositoryPort {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    void save(User user);
}