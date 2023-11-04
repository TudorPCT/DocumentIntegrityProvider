package fii.dip.api.security;

import fii.dip.api.security.filters.LoginAuthFilter;
import fii.dip.api.security.filters.TokenAuthenticationFilter;
import fii.dip.api.security.provider.LoginAuthProvider;
import fii.dip.api.security.provider.TokenAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenAuthProvider tokenAuthProvider;
    private final LoginAuthProvider loginAuthProvider;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public LoginAuthFilter loginAuthFilter() {
        return new LoginAuthFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            @Value("${security.allowed.paths}") final String[] paths,
            @Value("${security.allowed.origins}") final List<String> origins,
            @Value("${security.allowed.headers}") final List<String> headers,
            @Value("${security.allowed.methods}") final List<String> methods) throws Exception {

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Bean
    public AuthenticationManager providerManager() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
