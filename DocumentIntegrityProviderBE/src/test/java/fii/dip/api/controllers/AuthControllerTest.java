package fii.dip.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fii.dip.api.DocumentIntegrityProviderBeApplication;
import fii.dip.api.dtos.NewUserDto;
import fii.dip.api.exceptions.EmailAlreadyExistsException;
import fii.dip.api.exceptions.InvalidEmailFormatException;
import fii.dip.api.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = DocumentIntegrityProviderBeApplication.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

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

        given(userService.register(any(NewUserDto.class))).willReturn("User registered successfully");

        mockMvc.perform(
                post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUserDto)))
                .andExpect(status().isOk()
                ).andExpect(jsonPath("message").value("User registered successfully"));
    }

    @Test
    public void returnUser_When_InvalidValidData() throws Exception {
        NewUserDto newUserDto = NewUserDto.builder()
                .email("")
                .password("")
                .build();

        given(userService.register(any(NewUserDto.class))).willReturn("User registered successfully");

        mockMvc.perform(
                post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUserDto))
                ).andExpect(status().isBadRequest()
        );
    }

    @Test
    public void returnUser_When_InvalidValidData_EmailExists() {
        NewUserDto newUserDto = NewUserDto.builder()
                .email("cosmin@gmail.com")
                .password("cosmin")
                .build();

        given(userService.register(any(NewUserDto.class))).willThrow(new EmailAlreadyExistsException("Email already exists"));

        assertThrows(EmailAlreadyExistsException.class,
                () -> mockMvc.perform(
                        post("/api/register").servletPath("/api/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(newUserDto))
                )
        );
    }

    @Test
    public void returnUser_When_InvalidValidData_InvalidEmailFormat() {
        NewUserDto newUserDto = NewUserDto.builder()
                .email("cosmin")
                .password("cosmin")
                .build();

        given(userService.register(any(NewUserDto.class))).willThrow(new InvalidEmailFormatException("Invalid email format"));

        assertThrows(MethodArgumentNotValidException.class, () -> mockMvc.perform(post("/api/register").servletPath("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newUserDto))));
    }
}