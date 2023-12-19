package fii.dip.api.security.provider;

import fii.dip.api.security.authentication.AuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import fii.dip.api.security.services.JwtUtil;
import fii.dip.api.services.interfaces.UserService;
import io.jsonwebtoken.JwtException;

@Component
@RequiredArgsConstructor
public class TokenAuthProvider implements AuthenticationProvider {
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) (authentication.getCredentials());
        String id;

        id = jwtUtil.getIdFromToken(token);

        if (id == null || !jwtUtil.validateToken(token)) {
            throw new JwtException("Authentication error");
        }

        return new AuthenticationToken(userService.loadUserById(id), token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.equals(authentication);
    }
}
