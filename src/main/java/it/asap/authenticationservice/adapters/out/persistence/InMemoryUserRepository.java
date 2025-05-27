package it.asap.authenticationservice.adapters.out.persistence;

import it.asap.authenticationservice.application.port.out.UserRepositoryPort;
import it.asap.authenticationservice.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepositoryPort {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }
}