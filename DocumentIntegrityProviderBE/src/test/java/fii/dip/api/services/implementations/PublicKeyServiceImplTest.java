package fii.dip.api.services.implementations;

import fii.dip.api.exceptions.PublicKeyNotFoundException;
import fii.dip.api.exceptions.UserNotFoundException;
import fii.dip.api.models.PublicKey;
import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.repositories.PublicKeyRepository;
import fii.dip.api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PublicKeyServiceImplTest {

    @InjectMocks
    private PublicKeyServiceImpl publicKeyServiceImpl;

    @Mock
    private PublicKeyRepository publicKeyRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void returnPublicKey_WhenGetPublicKeyByUserId_ForProvidedId() {

        PublicKey publicKey = publicKeyBuilder();

        given(publicKeyRepository.findByUserId(any(String.class))).willReturn(java.util.Optional.of(publicKey));

        PublicKey publicKeyResult = publicKeyServiceImpl.getPublicKeyByUserId(publicKey.getUser().getId());

        assertThat(publicKeyResult).isNotNull();
        assertThat(publicKeyResult).isEqualTo(publicKey);
    }

    @Test
    void returnError_WhenGetPublicKeyByUserId_ForUnknownId() {

        given(publicKeyRepository.findByUserId(any(String.class))).willReturn(java.util.Optional.empty());

        assertThrows(PublicKeyNotFoundException.class, () -> publicKeyServiceImpl.getPublicKeyByUserId("1"));
    }

    @Test
    void returnPublicKey_WhenAddUserPublicKey() {
        PublicKey publicKey = publicKeyBuilder();

        given(publicKeyRepository.save(any(PublicKey.class))).willReturn(publicKey);
        given(userRepository.findById(any(String.class))).willReturn(java.util.Optional.of(publicKey.getUser()));

        PublicKey publicKeyResult = publicKeyServiceImpl.addUserPublicKey(publicKey.getPublicKey(), publicKey.getUser().getId());

        assertThat(publicKeyResult).isNotNull();
        assertThat(publicKeyResult).isEqualTo(publicKey);
    }

    @Test
    void returnError_WhenAddUserPublicKey_ForUnknownUserId() {
        PublicKey publicKey = publicKeyBuilder();

        given(userRepository.findById(any(String.class))).willReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> publicKeyServiceImpl.addUserPublicKey(publicKey.getPublicKey(), publicKey.getUser().getId()));
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