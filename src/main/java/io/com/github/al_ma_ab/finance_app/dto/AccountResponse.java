package io.com.github.al_ma_ab.finance_app.dto;

import io.com.github.al_ma_ab.finance_app.model.AccountRole;

import java.util.UUID;

public record AccountResponse (
        UUID accountId,
        String name,
        AccountRole role
){}
