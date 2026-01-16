package io.com.github.al_ma_ab.finance_app.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    /**
     * chave secreta Base64
     */
    private String secret;
    /**
     * Experiação do Token em minutos
     */
    private long expirationMinutes;
}
