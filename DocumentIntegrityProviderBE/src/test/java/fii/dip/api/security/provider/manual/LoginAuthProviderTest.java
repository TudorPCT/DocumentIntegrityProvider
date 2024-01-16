package fii.dip.api.security.provider.manual;

import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.security.model.UserSecurityDetails;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginAuthProviderTest {

    @Mock
    private UserSecurityDetailsService userSecurityDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginAuthProvider loginAuthProvider;

    @Test
    void returnAuthentication_ForAuthenticate_WhenValid() {
        User user = userBuilder();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        given(userSecurityDetailsService.loadUserByUsername(any(String.class))).willReturn(new UserSecurityDetails(user));
        given(passwordEncoder.matches(any(String.class), any(String.class))).willReturn(true);

        assertThat(loginAuthProvider.authenticate(authentication)).isNotNull();
    }

    @Test
    void returnAuthentication_ForAuthenticate_WhenInvalid() {
        User user = userBuilder();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        given(userSecurityDetailsService.loadUserByUsername(any(String.class))).willReturn(new UserSecurityDetails(user));
        given(passwordEncoder.matches(any(String.class), any(String.class))).willReturn(false);

        assertThrows(BadCredentialsException.class, () -> loginAuthProvider.authenticate(authentication));
    }

    @Test
    void supports() {
        assertTrue(loginAuthProvider.supports(UsernamePasswordAuthenticationToken.class));
    }

    private User userBuilder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .id("1")
                .email("cosmin@gmail.com")
                .password(passwordEncoder.encode("cosmin"))
                .role(Role.ROLE_USER)
                .version(1)
                .isAccountNonLocked(true)
                .createdAt(LocalDateTime.of(2023, 11, 10, 1, 0))
                .updatedAt(LocalDateTime.of(2023, 11, 10, 2, 0))
                .build();
    }
}