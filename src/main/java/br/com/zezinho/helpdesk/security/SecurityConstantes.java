package br.com.zezinho.helpdesk.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SecurityConstantes {
    @Value("${jwt.expiration}")
    Long JWT_EXPIRATION;

    @Value("${jwt.secret}")
    String JWT_SECRET;

}
