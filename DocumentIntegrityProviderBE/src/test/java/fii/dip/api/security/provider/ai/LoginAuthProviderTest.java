package fii.dip.api.security.provider.ai;

import fii.dip.api.security.provider.LoginAuthProvider;
import fii.dip.api.security.services.UserSecurityDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginAuthProviderTest {
    // generate tests

    @Mock
    private UserSecurityDetailsService userSecurityDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginAuthProvider loginAuthProvider;

    @Test
    void returnAuthentication_ForAuthenticate_WhenValid() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userSecurityDetailsService.loadUserByUsername(any(String.class))).thenReturn(userDetails);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);
        Authentication authentication = new UsernamePasswordAuthenticationToken("email", "password");
        assertNotNull(loginAuthProvider.authenticate(authentication));
    }

    @Test
    void returnAuthentication_ForAuthenticate_WhenInvalid() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userSecurityDetailsService.loadUserByUsername(any(String.class))).thenReturn(userDetails);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);
        Authentication authentication = new UsernamePasswordAuthenticationToken("email", "password");
        assertThrows(BadCredentialsException.class, () -> loginAuthProvider.authenticate(authentication));
    }

    @Test
    void supports() {
        assertTrue(loginAuthProvider.supports(UsernamePasswordAuthenticationToken.class));
    }

}
