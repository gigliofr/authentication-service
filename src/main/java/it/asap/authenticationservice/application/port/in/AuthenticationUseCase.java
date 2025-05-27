package it.asap.authenticationservice.application.port.in;

import it.asap.authenticationservice.adapters.in.web.model.AuthResponse;
import it.asap.authenticationservice.adapters.in.web.model.ChangePasswordRequest;
import it.asap.authenticationservice.adapters.in.web.model.LoginRequest;
import it.asap.authenticationservice.adapters.in.web.model.RegisterRequest;

public interface AuthenticationUseCase {
    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);

    void changePassword(ChangePasswordRequest request, String authorization);

    String validateToken(String token);
}