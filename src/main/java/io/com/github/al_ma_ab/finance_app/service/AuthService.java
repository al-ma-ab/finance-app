package io.com.github.al_ma_ab.finance_app.service;

import io.com.github.al_ma_ab.finance_app.dto.AuthResponse;
import io.com.github.al_ma_ab.finance_app.dto.LoginRequest;
import io.com.github.al_ma_ab.finance_app.dto.RegisterRequest;
import io.com.github.al_ma_ab.finance_app.exception.BusinessException;
import io.com.github.al_ma_ab.finance_app.model.Account;
import io.com.github.al_ma_ab.finance_app.model.AccountRole;
import io.com.github.al_ma_ab.finance_app.model.AccountUser;
import io.com.github.al_ma_ab.finance_app.model.User;
import io.com.github.al_ma_ab.finance_app.repository.AccountRepository;
import io.com.github.al_ma_ab.finance_app.repository.AccountUserRepository;
import io.com.github.al_ma_ab.finance_app.repository.UserRepository;
import io.com.github.al_ma_ab.finance_app.security.JwtService;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            AccountRepository accountRepository,
            AccountUserRepository accountUserRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountUserRepository = accountUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("E-mail já cadastrado");
        }

        // 1) cria e salva User
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);

        // 2) cria e salva Account
        Account account = new Account();
        account.setName("Conta principal");
        // se você tiver "type" ou outros campos, set aqui também
        account = accountRepository.save(account);

        // 3) cria vínculo AccountUser com role OWNER
        AccountUser accountUser = new AccountUser();
        accountUser.setAccount(account);
        accountUser.setUser(user);
        accountUser.setRole(AccountRole.OWNER);
        accountUserRepository.save(accountUser);

        // 4) gera token e retorna
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
    public String login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        // Por enquanto vamos retornar um token "fake" só para validar o fluxo.
        // Amanhã a gente troca isso por JWT de verdade.
        return jwtService.generateToken(user.getEmail());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.UNAUTHORIZED.value(),
                        "error", "Unauthorized",
                        "message", "Email ou senha inválidos"
                ));
    }

}

