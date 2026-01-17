package io.com.github.al_ma_ab.finance_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    // ex PERSONAL, FAMILY, BUSINESS
    private String type;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

}
