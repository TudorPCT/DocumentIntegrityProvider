package fii.dip.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fii.dip.api.dtos.NewUserDto;
import fii.dip.api.exceptions.EmailAlreadyExistsException;
import fii.dip.api.exceptions.InvalidEmailFormatException;
import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.services.interfaces.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
//        mvc = MockMvcBuilders.standaloneSetup(new HandlerController()).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void returnUser_When_ValidData() throws Exception {
        NewUserDto newUserDto = NewUserDto.builder()
                .email("cosmin@gmail.com")
                .password("cosmin")
                .build();

        given(userService.register(any(NewUserDto.class))).willReturn(userBuilder());

        mockMvc.perform(
                post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User registered successfully"));
    }

    @Test
    public void returnUser_When_InvalidValidData() throws Exception {
        NewUserDto newUserDto = NewUserDto.builder()
                .email("")
                .password("")
                .build();

        given(userService.register(any(NewUserDto.class))).willReturn(userBuilder());

        mockMvc.perform(
                post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUserDto))
                ).andExpect(status().isBadRequest()
        );
    }

    @Test
    public void returnUser_When_InvalidValidData_EmailExists() throws Exception {
        NewUserDto newUserDto = NewUserDto.builder()
                .email("cosmin@gmail.com")
                .password("cosmin")
                .build();

        given(userService.register(any(NewUserDto.class))).willThrow(new EmailAlreadyExistsException("Email already exists"));

        mockMvc.perform(
                post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUserDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value("Email already exists"));
    }

    @Test
    public void returnUser_When_InvalidValidData_InvalidEmailFormat() throws Exception {
        NewUserDto newUserDto = NewUserDto.builder()
                .email("cosmin")
                .password("cosmin")
                .build();

        given(userService.register(any(NewUserDto.class))).willThrow(new InvalidEmailFormatException("Invalid email format"));

        mockMvc.perform(
                post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUserDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value("Invalid email format"));
    }

    private User userBuilder() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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