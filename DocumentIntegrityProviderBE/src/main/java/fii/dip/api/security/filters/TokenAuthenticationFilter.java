package fii.dip.api.security.filters;

import fii.dip.api.security.authentication.AuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    protected boolean shouldNotFilter(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        token = token.substring(7);

        Authentication user = authenticationManager.authenticate(new AuthenticationToken(token));

        SecurityContextHolder.getContext().setAuthentication(user);

        filterChain.doFilter(request, response);
    }
}
