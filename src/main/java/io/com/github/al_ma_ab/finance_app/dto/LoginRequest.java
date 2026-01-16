package io.com.github.al_ma_ab.finance_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Email é obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    public LoginRequest(){
        // Para uso do framework
    }

    public LoginRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
}
