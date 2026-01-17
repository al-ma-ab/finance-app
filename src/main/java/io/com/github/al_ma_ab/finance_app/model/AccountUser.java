package io.com.github.al_ma_ab.finance_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "account_user",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"account_id", "user_id"}
        )
)

@Getter
@Setter
public class AccountUser {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;


}
