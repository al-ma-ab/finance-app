package io.com.github.al_ma_ab.finance_app.dto;

import lombok.Getter;

@Getter
public class AuthResponse {

    private final String token;

    public AuthResponse(String token){
        this.token = token;
    }
}
