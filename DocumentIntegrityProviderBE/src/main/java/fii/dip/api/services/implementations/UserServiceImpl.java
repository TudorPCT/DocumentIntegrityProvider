package fii.dip.api.services.implementations;

import fii.dip.api.dtos.NewUserDto;
import fii.dip.api.exceptions.EmailAlreadyExistsException;
import fii.dip.api.models.Role;
import fii.dip.api.models.User;
import fii.dip.api.repositories.UserRepository;
import fii.dip.api.security.model.UserSecurityDetails;
import fii.dip.api.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserById(String id) {
        Optional<User> user = userRepository.findById(id);

        return new UserSecurityDetails(user.get());
    }

    @Override
    public String register(NewUserDto newUserDto) {

        User newUser = new User();

        newUser.setEmail(newUserDto.getEmail());

        newUser.setPassword(passwordEncoder.encode(newUserDto.getPassword()));

        newUser.setRole(Role.ROLE_USER);

        userRepository.save(newUser);

        return "User registered successfully";
    }
}
