package fii.dip.api.security.provider.ai;

import fii.dip.api.security.authentication.AuthenticationToken;
import fii.dip.api.security.provider.TokenAuthProvider;
import fii.dip.api.services.interfaces.UserService;
import fii.dip.api.security.services.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ChatGPT didn't add this annotation and the tests failed
class TokenAuthProviderTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private TokenAuthProvider tokenAuthProvider;

    @Test
    void testAuthenticateSuccess() {
        // Arrange
        String token = "validToken";
        String userId = "123";
        UserDetails userDetails = mock(UserDetails.class);
        AuthenticationToken expectedAuthenticationToken = new AuthenticationToken(userDetails, token);

        when(jwtUtil.getIdFromToken(token)).thenReturn(userId);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(userService.loadUserById(userId)).thenReturn(userDetails);

        Authentication authentication = new AuthenticationToken(token);

        // Act
        Authentication result = tokenAuthProvider.authenticate(authentication);

        // Assert
        assertTrue(result.isAuthenticated());
        assertEquals(expectedAuthenticationToken, result);

        verify(jwtUtil, times(1)).getIdFromToken(token);
        verify(jwtUtil, times(1)).validateToken(token);
        verify(userService, times(1)).loadUserById(userId);
    }

    @Test
    void testAuthenticateFailureInvalidToken() {
        // Arrange
        String token = "invalidToken";

        when(jwtUtil.validateToken(token)).thenReturn(false);

        Authentication authentication = new AuthenticationToken(token);

        // Act & Assert
        assertThrows(JwtException.class, () -> tokenAuthProvider.authenticate(authentication));

        verify(jwtUtil, times(1)).validateToken(token);
        verifyNoInteractions(userService);
    }

    @Test
    void testAuthenticateFailureExpiredToken() {
        // Arrange
        String token = "expiredToken";

        when(jwtUtil.validateToken(token)).thenThrow(ExpiredJwtException.class);

        Authentication authentication = new AuthenticationToken(token);

        // Act & Assert
        assertThrows(JwtException.class, () -> tokenAuthProvider.authenticate(authentication));

        verify(jwtUtil, times(1)).validateToken(token);
        verifyNoInteractions(userService);
    }

    @Test
    void testSupports() {
        // Arrange
        Class<?> supportedAuthentication = AuthenticationToken.class;
        Class<?> unsupportedAuthentication = Object.class;

        // Act & Assert
        assertTrue(tokenAuthProvider.supports(supportedAuthentication));
        assertFalse(tokenAuthProvider.supports(unsupportedAuthentication));
    }
}
