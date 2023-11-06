package fii.dip.api.services.implementations;

import fii.dip.api.dtos.NewUserDto;
import fii.dip.api.repositories.UserRepository;
import fii.dip.api.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserById(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String register(NewUserDto newUserDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
