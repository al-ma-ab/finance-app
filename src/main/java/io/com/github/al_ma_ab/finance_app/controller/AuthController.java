package io.com.github.al_ma_ab.finance_app.controller;

import io.com.github.al_ma_ab.finance_app.dto.RegisterRequest;
import io.com.github.al_ma_ab.finance_app.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.com.github.al_ma_ab.finance_app.dto.AuthResponse;
import io.com.github.al_ma_ab.finance_app.dto.LoginRequest;
import io.com.github.al_ma_ab.finance_app.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Cadastro de usuário
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Login de usuário
     */
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
//       String token = authService.login(request);
//        return ResponseEntity.ok(new AuthResponse(token));
//    }
}
