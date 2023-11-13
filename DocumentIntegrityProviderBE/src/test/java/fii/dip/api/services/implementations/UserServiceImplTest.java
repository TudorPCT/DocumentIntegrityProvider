package fii.dip.api.services.implementations;

import fii.dip.api.dtos.NewUserDto;
import fii.dip.api.exceptions.EmailAlreadyExistsException;
import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    public void returnUserDetails_WhenLoadUserById_ForProvidedId() {
        User user = userBuilder();

        given(userRepository.findById(user.getId())).willReturn(java.util.Optional.of(user));

        UserDetails userDetails = userServiceImpl.loadUserById(user.getId());

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(user.getId());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDetails.getAuthorities()).isNotNull();
        assertThat(userDetails.getAuthorities().size()).isEqualTo(1);
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo(Role.ROLE_USER.name());
    }

    @Test
    public void returnMessage_WhenUserRegisteredSuccessfully() {
        NewUserDto newUserDto = NewUserDto.builder()
                .email("cosmin@gmail.com")
                .password("cosmin")
                .build();

        User user = userBuilder();

        given(userRepository.save(any(User.class))).willReturn(user);
        given(passwordEncoder.encode(newUserDto.getPassword())).willReturn("cosmin");

        String response = userServiceImpl.register(newUserDto);

        assertThat(response).isEqualTo("User registered successfully");
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