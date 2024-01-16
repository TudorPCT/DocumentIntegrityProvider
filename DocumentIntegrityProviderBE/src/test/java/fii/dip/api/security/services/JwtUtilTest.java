package fii.dip.api.security.services;

import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.security.model.UserSecurityDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil.setSecret("secret");
    }

    @Test
    void returnToken_WhenGenerateToken() {

        UserDetails userDetails = new UserSecurityDetails(userBuilder());

        String token = jwtUtil.generateToken(userDetails);

        assertNotNull(token);
    }

    @Test
    void returnUserId_WhenGetUserIdFromToken() {

        UserDetails userDetails = new UserSecurityDetails(userBuilder());

        String token = jwtUtil.generateToken(userDetails);

        String userId = jwtUtil.getIdFromToken(token);

        assertEquals(userDetails.getUsername(), userId);
    }

    @Test
    void returnTrue_WhenValidateToken() {

        UserDetails userDetails = new UserSecurityDetails(userBuilder());

        String token = jwtUtil.generateToken(userDetails);

        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void returnFalse_WhenValidateToken() {

        UserDetails userDetails = new UserSecurityDetails(userBuilder());

        String token = jwtUtil.generateToken(userDetails);

        assertFalse(jwtUtil.validateToken(token + "1"));
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