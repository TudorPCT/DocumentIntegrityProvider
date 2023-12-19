package fii.dip.api.security.provider;

import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.security.authentication.AuthenticationToken;
import fii.dip.api.security.model.UserSecurityDetails;
import fii.dip.api.security.services.JwtUtil;
import fii.dip.api.services.interfaces.UserService;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TokenAuthProviderTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private TokenAuthProvider tokenAuthProvider;

    @Test
    void returnAuthentication_ForAuthenticate_WhenValidToken() {
        User user = userBuilder();
        Authentication authentication = new AuthenticationToken(new UserSecurityDetails(user), "token");

        given(jwtUtil.getIdFromToken("token")).willReturn(user.getId());
        given(jwtUtil.validateToken(any(String.class))).willReturn(true);
        given(userService.loadUserById(user.getId())).willReturn(new UserSecurityDetails(user));

        Authentication result = tokenAuthProvider.authenticate(authentication);

        assertThat(result).isNotNull();
        assertThat(result.getPrincipal()).isNotNull();

        UserSecurityDetails userSecurityDetails = new UserSecurityDetails(user);
        UserSecurityDetails resultUserSecurityDetails = (UserSecurityDetails) result.getPrincipal();

        assertThat(resultUserSecurityDetails.getUsername()).isEqualTo(userSecurityDetails.getUsername());
        assertThat(resultUserSecurityDetails.getPassword()).isEqualTo(userSecurityDetails.getPassword());
        assertThat(resultUserSecurityDetails.getAuthorities()).isEqualTo(userSecurityDetails.getAuthorities());
        assertThat(resultUserSecurityDetails.isAccountNonLocked()).isEqualTo(userSecurityDetails.isAccountNonLocked());
        assertThat(resultUserSecurityDetails.isEnabled()).isEqualTo(userSecurityDetails.isEnabled());
        assertThat(resultUserSecurityDetails.isAccountNonExpired()).isEqualTo(userSecurityDetails.isAccountNonExpired());
        assertThat(resultUserSecurityDetails.isCredentialsNonExpired()).isEqualTo(userSecurityDetails.isCredentialsNonExpired());
    }

    @Test
    void returnError_ForAuthenticate_WhenInvalidToken() {
        User user = userBuilder();
        Authentication authentication = new AuthenticationToken(new UserSecurityDetails(user), "token");

        given(jwtUtil.getIdFromToken("token")).willReturn(user.getId());
        given(jwtUtil.validateToken(any(String.class))).willReturn(false);

        assertThrows(JwtException.class, () -> tokenAuthProvider.authenticate(authentication));
    }

    @Test
    void supports() {
        assertTrue(tokenAuthProvider.supports(AuthenticationToken.class));
    }

    private User userBuilder() {
        return User.builder()
                .id("1")
                .email("cosmin@gmail.com")
                .password("cosmin")
                .role(Role.ROLE_USER)
                .version(1)
                .isAccountNonLocked(true)
                .createdAt(LocalDateTime.of(2023, 11, 10, 1, 0))
                .updatedAt(LocalDateTime.of(2023, 11, 10, 2, 0))
                .build();
    }
}