package fii.dip.api.controllers;

import fii.dip.api.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.time.LocalDateTime;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ControllerAudit{

    @Before("register() && execution(* createUser*(..)) && args(user,..)")
    private void logCreateUser(User user) {
        log.info("Received request to create user: " + user.toString());
    }

    @After("register() && execution(* createUser*(..)) && args(user,..)")
    private void logCreateUser(User user) {
        log.info("Created user: " + user.toString());
    }


    @Before("register()  && execution(* deleteUser*(..)) && args(userId,..)")
    private void logDeleteUser(Long userId) {
        log.info("Received request to delete user with ID: " + userId.toString());
    }

    @After("register()  && execution(* deleteUser*(..)) && args(userId,..)")
    private void logDeleteUser(Long userId) {
        log.info("Deleted user with ID: " + userId.toString());
    }


    @Before("register()  && execution(* retrieveById*(..)) && args(userId,..)")
    private void logRetrieveUser(Long userId) {
        log.info("Received request to retrieve user with ID: " + userId.toString());
    }

    @Before("register()  && execution(* retrieveAll*(..))")
    private void logRetrieveAllUsers() {
        log.info("Received request to retrieve all users");
    }

    @Before("register()  && execution(* updateUser*(..)) && args(userId,..)")
    private void logUpdateUser(Long userId) {
        log.info("Received request to update user with ID: " + userId.toString());
    }

    @Before("register()  && execution(* login*(..)) && args(user,..)")
    private void logLoginUser(User user) {
        log.info("Received request to login user: " + user.toString());
    }

    @After("register()  && execution(* login*(..)) && args(user,..)")
    private void logLoginUser(User user) {
        log.info("Login user: " + user.toString());
    }

    @Before("register()  && execution(* logout*(..)) && args(user,..)")
    private void logLogoutUser(User user) {
        log.info("Received request to logout user: " + user.toString());
    }

    @After("register()  && execution(* logout*(..)) && args(user,..)")
    private void logLogoutUser(User user) {
        log.info("Logout user: " + user.toString());
    }

    @Before("register()  && execution(* changePassword*(..)) && args(user,..)")
    private void logChangePasswordUser(User user) {
        log.info("Received request to change password for user: " + user.toString());
    }


    @After("register()  && execution(* changePassword*(..)) && args(user,..)")
    private void logChangePasswordUser(User user) {
        log.info("Change password for user: " + user.toString());
    }

    @Before("register()  && execution(* resetPassword*(..)) && args(user,..)")
    private void logResetPasswordUser(User user) {
        log.info("Received request to reset password for user: " + user.toString());
    }


    @After("register()  && execution(* resetPassword*(..)) && args(user,..)")
    private void logResetPasswordUser(User user) {
        log.info("Reset password for user: " + user.toString());
    }

    @Before("register()  && execution(* changeEmail*(..)) && args(user,..)")
    private void logChangeEmailUser(User user) {
        log.info("Received request to change email for user: " + user.toString());
    }


    @After("register()  && execution(* changeEmail*(..)) && args(user,..)")
    private void logChangeEmailUser(User user) {
        log.info("Change email for user: " + user.toString());
    }


//    @Before("register()  && execution(* resetEmail*(..)) && args(user,..)")
//    private void logResetEmailUser(User user) {
//        log.info("Received request to reset email for user: " + user.toString());
//    }

    @Before("register()  && execution(* changeUsername*(..)) && args(user,..)")
    private void logChangeUsernameUser(User user) {
        log.info("Received request to change username for user: " + user.toString());
    }

    @After("register()  && execution(* changeUsername*(..)) && args(user,..)")
    private void logChangeUsernameUser(User user) {
        log.info("Change username for user: " + user.toString());
    }


    @Before("register()  && execution(* changeDocument*(..)) && args(user,..)")
    private void logChangeDocumentUser(User user) {
        log.info("Received request to change document for user: " + user.toString());
    }


    @Before("register()  && execution(* signDocument*(..)) && args(user,..)")
    private void logSignDocumentUser(User user) {
        log.info("Received request to sign document for user: " + user.toString());
    }


    @Before("register()  && execution(* resignDocument*(..)) && args(user,..)")
    private void logResignDocumentUser(User user) {
        log.info("Received request to resign document for user: " + user.toString());
    }


    @Before("register()  && execution(* addSignature*(..)) && args(user,..)")
    private void logAddSignatureUser(User user) {
        log.info("Received request to add signature for user: " + user.toString());
    }


    @After("register()  && execution(* addSignature*(..)) && args(user,..)")
    private void logAfterAddSignatureUser(User user) {
        log.info("Signature added for user: " + user.toString());
    }


    @Before("register()  && execution(* removeSignature*(..)) && args(user,..)")
    private void logRemoveSignatureUser(User user) {
        log.info("Received request to remove signature for user: " + user.toString());
    }


    @After("register()  && execution(* removeSignature*(..)) && args(user,..)")
    private void logAfterRemoveSignatureUser(User user) {
        log.info("Signature removed for user: " + user.toString());
    }


    @Before("register()  && execution(* addDocument*(..)) && args(user,..)")
    private void logAddDocumentUser(User user) {
        log.info("Received request to add document for user: " + user.toString());
    }


    @Before("register()  && execution(* removeDocument*(..)) && args(user,..)")
    private void logRemoveDocumentUser(User user) {
        log.info("Received request to remove document for user: " + user.toString());
    }


    @After("register()  && execution(* removeDocument*(..)) && args(user,..)")
    private void logAfterRemoveDocumentUser(User user) {
        log.info("Document removed for user: " + user.toString());
    }


    @Before("register()  && execution(* modifyDocument*(..)) && args(user,..)")
    private void logModifyDocumentUser(User user) {
        log.info("Received request to modify document for user: " + user.toString());
    }


    @Before("register()  && execution(* accesDocument*(..)) && args(user,..)")
    private void logAccesDocumentUser(User user) {
        log.info("Received request to acces document for user: " + user.toString());
    }
}