package fii.dip.api.controllers;

import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void returnError_WhenBadPassword() {
        User user = userBuilder();

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        assertThrows(BadCredentialsException.class, () -> mockMvc.perform(post("/api/login").servletPath("/api/login")
                .header("email", user.getEmail())
                .header("password", user.getPassword() + "t")
                .contentType(MediaType.APPLICATION_JSON)));
    }

    @Test
    public void returnToken_WhenLoginWorks() throws Exception {
        User user = userBuilder();
        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        mockMvc.perform(post("/api/login").servletPath("/api/login")
                        .header("email", user.getEmail())
                        .header("password", "cosmin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void returnError_WhenUserNotFound() {
        User user = userBuilder();
        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> mockMvc.perform(post("/api/login").servletPath("/api/login")
                .header("email", user.getEmail())
                .header("password", user.getPassword())
                .contentType(MediaType.APPLICATION_JSON)));
    }

    @Test
    public void returnError_WhenBadEmail() {
        User user = userBuilder();
        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        assertThrows(BadCredentialsException.class, () -> mockMvc.perform(post("/api/login").servletPath("/api/login")
                .header("email", user.getEmail() + "t")
                .header("password", user.getPassword())
                .contentType(MediaType.APPLICATION_JSON)));
    }

    private User userBuilder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .id("1")
                .email("cosmin@gmail.com")
                .password(passwordEncoder.encode("cosmin"))
                .role(Role.ROLE_USER)
//                .version(1)
//                .isAccountNonLocked(true)
//                .createdAt(LocalDateTime.of(2023, 11, 10, 1, 0))
//                .updatedAt(LocalDateTime.of(2023, 11, 10, 2, 0))
                .build();
    }
}
