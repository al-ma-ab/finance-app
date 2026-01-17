package io.com.github.al_ma_ab.finance_app.repository;

import io.com.github.al_ma_ab.finance_app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
