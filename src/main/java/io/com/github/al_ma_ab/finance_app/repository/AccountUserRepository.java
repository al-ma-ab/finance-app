package io.com.github.al_ma_ab.finance_app.repository;

import io.com.github.al_ma_ab.finance_app.model.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountUserRepository extends JpaRepository<AccountUser, UUID> {

    //Lista contas que o usuário participa
    List<AccountUser> findByUserId(UUID userId);

    //  Listas usuários de uma conta
    List<AccountUser> findByAccountId(UUID accountId);

    // Busca o vínculo especifico (evita duplicidade e ajuda em validações)
    Optional<AccountUser> findByAccountIdAndUserId(UUID accountId, UUID userId);

    // vrifica se a conta existe
    boolean existsByAccountIdAndUserId(UUID accountId, UUID userId);
}
