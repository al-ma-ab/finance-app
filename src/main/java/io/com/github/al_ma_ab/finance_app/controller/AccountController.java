package io.com.github.al_ma_ab.finance_app.controller;

import io.com.github.al_ma_ab.finance_app.dto.AccountResponse;
import io.com.github.al_ma_ab.finance_app.dto.CreateAccountRequest;
import io.com.github.al_ma_ab.finance_app.model.Account;
import io.com.github.al_ma_ab.finance_app.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){

        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponse> myAccounts(Authentication authentication){
        String email = authentication.getName(); //subject do Jwt
        return accountService.listMyAccounts(email);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(
            @Valid @RequestBody CreateAccountRequest request,
            Authentication authentication
    ) {
        Account account = accountService.createAccount(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }


}
