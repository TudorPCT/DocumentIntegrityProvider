package fii.dip.api.aspects;

import fii.dip.api.dtos.NewUserDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SecurityAspect {

    @Pointcut("within(fii.dip.api.services.interfaces.UserService)")
    private void anyUserServiceMethod(){}

    @Before("anyUserServiceMethod() && execution(* loadUserById*(..)) && args(id, ..)")
    private void logLoadUserById(String id){
        log.info("Received request to load user with ID: " + id);
    }

    @Before("anyUserServiceMethod() && execution(* register*(..)) && args(newUserDto, ..)")
    private void logRegisterUser(NewUserDto newUserDto){
        log.info("Received request to register user: " + newUserDto.toString());
    }



}
