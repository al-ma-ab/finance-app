package io.com.github.al_ma_ab.finance_app.repository;

import io.com.github.al_ma_ab.finance_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
