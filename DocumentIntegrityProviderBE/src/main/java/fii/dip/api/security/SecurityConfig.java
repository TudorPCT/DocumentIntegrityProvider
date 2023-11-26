package fii.dip.api.security;

import fii.dip.api.models.Role;
import fii.dip.api.security.filters.LoginAuthFilter;
import fii.dip.api.security.filters.TokenAuthenticationFilter;
import fii.dip.api.security.provider.LoginAuthProvider;
import fii.dip.api.security.provider.TokenAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final TokenAuthProvider tokenAuthProvider;
    private final LoginAuthProvider loginAuthProvider;

    @Autowired
    public SecurityConfig(TokenAuthProvider tokenAuthProvider, LoginAuthProvider loginAuthProvider) {
        this.tokenAuthProvider = tokenAuthProvider;
        this.loginAuthProvider = loginAuthProvider;
    }

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

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.addFilterAt(loginAuthFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(tokenAuthenticationFilter(), BasicAuthenticationFilter.class);

        httpSecurity.authorizeHttpRequests(
                (authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(paths)
                        .permitAll().anyRequest().hasAuthority(Role.ROLE_USER.toString())
        );

        httpSecurity.cors(cors -> {
            CorsConfigurationSource corsConfigurationSource = r -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(origins);
                corsConfiguration.setAllowedMethods(methods);
                corsConfiguration.setAllowedHeaders(headers);
                return corsConfiguration;
            };

            cors.configurationSource(corsConfigurationSource);
        });

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager providerManager() {
        return new ProviderManager(tokenAuthProvider, loginAuthProvider);
    }

}
