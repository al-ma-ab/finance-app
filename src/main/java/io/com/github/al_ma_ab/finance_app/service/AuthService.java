package io.com.github.al_ma_ab.finance_app.service;

import io.com.github.al_ma_ab.finance_app.dto.LoginRequest;
import io.com.github.al_ma_ab.finance_app.dto.RegisterRequest;
import io.com.github.al_ma_ab.finance_app.model.User;
import io.com.github.al_ma_ab.finance_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public String login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        // Por enquanto vamos retornar um token "fake" só para validar o fluxo.
        // Amanhã a gente troca isso por JWT de verdade.
        return "TOKEN_TEMPORARIO";
    }


}

