package io.com.github.al_ma_ab.finance_app.service;

import io.com.github.al_ma_ab.finance_app.dto.AccountResponse;
import io.com.github.al_ma_ab.finance_app.dto.CreateAccountRequest;
import io.com.github.al_ma_ab.finance_app.exception.ResourceNotFoundException;
import io.com.github.al_ma_ab.finance_app.model.Account;
import io.com.github.al_ma_ab.finance_app.model.AccountRole;
import io.com.github.al_ma_ab.finance_app.model.AccountUser;
import io.com.github.al_ma_ab.finance_app.model.User;
import io.com.github.al_ma_ab.finance_app.repository.AccountRepository;
import io.com.github.al_ma_ab.finance_app.repository.AccountUserRepository;
import io.com.github.al_ma_ab.finance_app.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountUserRepository accountUserRepository;

    public AccountService(
            AccountRepository accountRepository,
            UserRepository userRepository,
            AccountUserRepository accountUserRepository
    ) {
        this.accountRepository = accountRepository;
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

    public Account createAccount(CreateAccountRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Account account = new Account();
        account.setName(request.getName());

        Account savedAccount = accountRepository.save(account);

        AccountUser accountUser = new AccountUser();
        accountUser.setAccount(savedAccount);
        accountUser.setUser(user);
        accountUser.setRole(AccountRole.OWNER);

        accountUserRepository.save(accountUser);

        return savedAccount;
    }

    public void inviteUser(UUID accountId, String requesterEmail, String invitedEmail) {

        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado"));

        User invitedUser = userRepository.findByEmail(invitedEmail)
                .orElseThrow(() -> new RuntimeException("Usuário convidado não encontrado"));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        AccountUser requesterAccountUser = accountUserRepository
                .findByAccountIdAndUserId(accountId, requester.getId())
                .orElseThrow(() -> new RuntimeException("Você não possui acesso a esta conta"));

        if (requesterAccountUser.getRole() != AccountRole.OWNER) {
            throw new RuntimeException("Apenas o dono da conta pode convidar usuários");
        }

        boolean alreadyHasAccess = accountUserRepository
                .existsByAccountIdAndUserId(accountId, invitedUser.getId());

        if (alreadyHasAccess) {
            throw new RuntimeException("Usuário já possui acesso a esta conta");
        }

        AccountUser accountUser = new AccountUser();
        accountUser.setAccount(account);
        accountUser.setUser(invitedUser);
        accountUser.setRole(AccountRole.MEMBER);

        accountUserRepository.save(accountUser);
    }
}
