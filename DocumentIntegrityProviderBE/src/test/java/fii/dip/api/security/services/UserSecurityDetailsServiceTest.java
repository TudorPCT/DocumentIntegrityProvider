package fii.dip.api.security.services;

import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserSecurityDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSecurityDetailsService userSecurityDetailsService;

    @Test
    void returnUserDetails_WhenLoadUserByUsername_ForValidEmail() {
        User user = userBuilder();

        given(userRepository.findByEmail(any(String.class))).willReturn(java.util.Optional.of(user));

        assertThat(userSecurityDetailsService.loadUserByUsername(user.getEmail())).isNotNull();
    }

    @Test
    void returnUserDetails_WhenLoadUserByUsername_ForInvalidEmail() {
        User user = userBuilder();

        given(userRepository.findByEmail(any(String.class))).willReturn(java.util.Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userSecurityDetailsService.loadUserByUsername(user.getEmail()));
    }

    private User userBuilder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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