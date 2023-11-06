package fii.dip.api.security.services;

import java.io.Serializable;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil implements Serializable {
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails UserDetails) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getUsernameFromToken(String token) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean validateToken(String token) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
