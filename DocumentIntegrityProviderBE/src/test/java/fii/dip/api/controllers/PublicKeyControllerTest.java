package fii.dip.api.controllers;

import fii.dip.api.dtos.PublicKeyDto;
import fii.dip.api.exceptions.PublicKeyNotFoundException;
import fii.dip.api.models.PublicKey;
import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.services.interfaces.PublicKeyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PublicKeyController.class)
@ExtendWith(SpringExtension.class)
class PublicKeyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicKeyService publicKeyService;

    @Test
    void returnPublicKey_WhenRetrieve() throws Exception {

        PublicKey publicKey = publicKeyBuilder();
        given(publicKeyService.getPublicKeyByUserId(publicKey.getUser().getId())).willReturn(publicKey);

        mockMvc.perform(get("/api/public-key/" + publicKey.getUser().getId()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("1").password("admin").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("publicKey").value(publicKey.getPublicKey()));

    }

    @Test
    void returnError_WhenRetrieve_ForPublicKeyNotFound() throws Exception {

        PublicKey publicKey = publicKeyBuilder();
        given(publicKeyService.getPublicKeyByUserId(publicKey.getUser().getId())).willThrow(new PublicKeyNotFoundException("Public key not found"));

        mockMvc.perform(get("/api/public-key/" + publicKey.getUser().getId()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("1").password("admin").roles("USER")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Public key not found"));

    }

    @Test
    void returnOk_WhenAdd() throws Exception {

        PublicKey publicKey = publicKeyBuilder();
        PublicKeyDto publicKeyDto = new PublicKeyDto(publicKey.getPublicKey());

        given(publicKeyService.addUserPublicKey(any(String.class), any(String.class))).willReturn(publicKey);

        mockMvc.perform(post("/api/public-key").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publicKeyDto))
                        .with(user("1").password("admin").roles("USER")))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey publicKeyBuilder() {
        return new PublicKey(
                "1",
                "publicKey",
                userBuilder(),
                1,
                LocalDateTime.now());
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