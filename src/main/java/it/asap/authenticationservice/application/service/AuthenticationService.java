package it.asap.authenticationservice.application.service;

import it.asap.authenticationservice.adapters.in.web.model.AuthResponse;
import it.asap.authenticationservice.adapters.in.web.model.ChangePasswordRequest;
import it.asap.authenticationservice.adapters.in.web.model.LoginRequest;
import it.asap.authenticationservice.adapters.in.web.model.RegisterRequest;
import it.asap.authenticationservice.application.port.in.AuthenticationUseCase;
import it.asap.authenticationservice.application.port.out.UserRepositoryPort;
import it.asap.authenticationservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepositoryPort.findByUsername(request.getUsername());
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Credenziali non valide");
        }
        AuthResponse response = new AuthResponse();
        response.setJwtToken("jwt-mock-token");
        response.setExpiresIn(3600L);
        return response;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepositoryPort.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Utente già esistente");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userRepositoryPort.save(user);

        AuthResponse response = new AuthResponse();
        response.setJwtToken("jwt-mock-token");
        response.setExpiresIn(3600L);
        return response;
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String authorization) {
        // Si assume che il campo username sia già impostato dal controller
        if (request.getUsername() == null) {
            throw new RuntimeException("Username mancante nella richiesta");
        }
        User user = userRepositoryPort.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("Utente non trovato");
        }
        user.setPassword(request.getNewPassword());
        userRepositoryPort.save(user);
    }

    @Override
    public String validateToken(String token) {
        // Mock: in produzione validare il JWT e restituire l'username
        if ("jwt-mock-token".equals(token)) {
            return "mockUsername";
        }
        throw new RuntimeException("Token non valido");
    }
}