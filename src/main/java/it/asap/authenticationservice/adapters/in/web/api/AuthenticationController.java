package it.asap.authenticationservice.adapters.in.web.api;

import it.asap.authenticationservice.adapters.in.web.model.*;
import it.asap.authenticationservice.application.port.in.AuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements DefaultApi {

    private final AuthenticationUseCase authenticationUseCase;

    @Override
    public ResponseEntity<AuthResponse> authLoginPost(LoginRequest loginRequest) {
        AuthResponse response = authenticationUseCase.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthResponse> authRegisterPost(RegisterRequest registerRequest) {
        AuthResponse response = authenticationUseCase.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> authChangePasswordPost(ChangePasswordRequest changePasswordRequest) {
        String authorization = getAuthorizationHeader();
        String token = authorization.replace("Bearer ", "");
        String username = authenticationUseCase.validateToken(token);
        // Se serve, imposta l'username nella request
        // changePasswordRequest.setUsername(username);
        authenticationUseCase.changePassword(changePasswordRequest,token);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AuthValidateTokenGet200Response> authValidateTokenGet() {
        String authorization = getAuthorizationHeader();
        String token = authorization.replace("Bearer ", "");
        String username = authenticationUseCase.validateToken(token);
        AuthValidateTokenGet200Response response = new AuthValidateTokenGet200Response();
        response.setUsername(username);
        return ResponseEntity.ok(response);
    }

    private String getAuthorizationHeader() {
        return getRequest()
                .map(r -> r.getHeader("Authorization"))
                .orElseThrow(() -> new RuntimeException("Header Authorization mancante"));
    }
}