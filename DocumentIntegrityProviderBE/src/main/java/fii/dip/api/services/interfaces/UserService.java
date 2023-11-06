package fii.dip.api.services.interfaces;

import fii.dip.api.dtos.NewUserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserById(String id);
    String register(NewUserDto newUserDto);
}
