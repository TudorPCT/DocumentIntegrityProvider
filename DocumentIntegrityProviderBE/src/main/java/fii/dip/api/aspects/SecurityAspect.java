package fii.dip.api.aspects;

import fii.dip.api.models.PublicKey;
import fii.dip.api.models.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SecurityAspect {

    @Pointcut("within(fii.dip.api.services.implementations.UserServiceImpl)")
    private void anyUserServiceMethod(){}

    @Before("anyUserServiceMethod() && execution(* loadUserById*(..)) && args(id, ..)")
    private void logLoadUserById(String id){
        log.info("Received request to load user with ID: " + id);
    }

    @Around("anyUserServiceMethod() && execution(* register*(..))")
    private User logRegisterUser(ProceedingJoinPoint joinPoint){
        try {
            User newUser = (User) joinPoint.proceed();

            log.info("Registered user with ID: " + newUser.getId());

            return newUser;
        } catch (Throwable e) {
            log.info("Error while registering user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Pointcut("within(fii.dip.api.services.implementations.PublicKeyServiceImpl)")
    private void anyPublicKeyServiceMethod(){}

    @Before("anyPublicKeyServiceMethod() && execution(* getPublicKeyByUserId*(..)) && args(userId, ..)")
    private void logGetPublicKeyByUserId(String userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String callerId = (String) authentication.getPrincipal();

        log.info("Received request to get public key for user with ID: " + userId + " from user with ID: " + callerId);
    }

    @Around("anyPublicKeyServiceMethod() && execution(* addUserPublicKey*(..))")
    private PublicKey logAddUserPublicKey(ProceedingJoinPoint joinPoint){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();

        try {
            PublicKey publicKey = (PublicKey) joinPoint.proceed();

            log.info("User with ID: " + userId + " added public key with ID: " + publicKey.getId());

            return publicKey;
        } catch (Throwable e) {
            log.info("Error while adding public key for user with ID: " + userId + "; Message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }



}
