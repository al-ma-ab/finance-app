package io.com.github.al_ma_ab.finance_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InviteMemberRequest (
        @NotBlank @Email String email
){}
