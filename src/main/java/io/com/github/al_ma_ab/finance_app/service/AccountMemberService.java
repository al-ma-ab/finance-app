package io.com.github.al_ma_ab.finance_app.service;

import io.com.github.al_ma_ab.finance_app.dto.AccountResponse;
import io.com.github.al_ma_ab.finance_app.exception.ResourceNotFoundException;
import io.com.github.al_ma_ab.finance_app.model.AccountRole;
import io.com.github.al_ma_ab.finance_app.model.AccountUser;
import io.com.github.al_ma_ab.finance_app.repository.AccountRepository;
import io.com.github.al_ma_ab.finance_app.repository.AccountUserRepository;
import io.com.github.al_ma_ab.finance_app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;




import java.util.UUID;

@Service
public class AccountMemberService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;

    public AccountMemberService(
            UserRepository userRepository,
            AccountRepository accountRepository,
            AccountUserRepository accountUserRepository
    ) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountUserRepository = accountUserRepository;
    }

    @Transactional
    public AccountResponse inviteMember(UUID accountId, String inviterEmail, String invitedEmail) {

        var inviter = userRepository.findByEmail(inviterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário logado não encontrado"));

        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        // 1) Checar se inviter é OWNER da conta
        var inviterLink = accountUserRepository.findByAccountIdAndUserId(accountId, inviter.getId())
                .orElseThrow(() -> new AccessDeniedException("Você não participa desta conta"));

        if (inviterLink.getRole() != AccountRole.OWNER) {
            throw new AccessDeniedException("Somente OWNER pode convidar membros");
        }

        // 2) Buscar usuário convidado
        var invited = userRepository.findByEmail(invitedEmail)
                .orElseThrow(() -> new IllegalArgumentException("Usuário convidado não encontrado"));

        // 3) Evitar duplicidade
        if (accountUserRepository.findByAccountIdAndUserId(accountId, invited.getId()).isPresent()) {
            // já é membro/owner: devolve estado atual
            var existing = accountUserRepository.findByAccountIdAndUserId(accountId, invited.getId()).get();
            return new AccountResponse(account.getId(), account.getName(), existing.getRole());
        }

        // 4) Criar vínculo como MEMBER
        AccountUser link = new AccountUser();
        link.setAccount(account);
        link.setUser(invited);
        link.setRole(AccountRole.MEMBER);
        accountUserRepository.save(link);

        return new AccountResponse(account.getId(), account.getName(), AccountRole.MEMBER);
    }

}
