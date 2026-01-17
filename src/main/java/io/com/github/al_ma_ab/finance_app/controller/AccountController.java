package io.com.github.al_ma_ab.finance_app.controller;

import io.com.github.al_ma_ab.finance_app.dto.AccountResponse;
import io.com.github.al_ma_ab.finance_app.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
