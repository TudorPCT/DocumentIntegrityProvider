package fii.dip.api.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import fii.dip.api.security.services.JwtUtil;
import fii.dip.api.services.interfaces.UserService;

@Component
@RequiredArgsConstructor
public class TokenAuthProvider implements AuthenticationProvider {
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
