package fii.dip.api.services.interfaces;

import fii.dip.api.dtos.NewUserDto;
import fii.dip.api.models.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserById(String id);
    User register(NewUserDto newUserDto);
}
