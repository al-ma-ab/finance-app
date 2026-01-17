package io.com.github.al_ma_ab.finance_app.service;

import io.com.github.al_ma_ab.finance_app.dto.AccountResponse;
import io.com.github.al_ma_ab.finance_app.repository.AccountUserRepository;
import io.com.github.al_ma_ab.finance_app.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountUserRepository accountUserRepository;

    public AccountService(UserRepository userRepository, AccountUserRepository accountUserRepository) {
        this.userRepository = userRepository;
        this.accountUserRepository = accountUserRepository;
    }

    public List<AccountResponse> listMyAccounts(String email) {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return accountUserRepository.findByUserId(user.getId())
                .stream()
                .map(au -> new AccountResponse(
                        au.getAccount().getId(),
                        au.getAccount().getName(),
                        au.getRole()
                ))
                .toList();
    }
}
