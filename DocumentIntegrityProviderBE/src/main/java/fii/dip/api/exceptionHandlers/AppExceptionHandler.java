package fii.dip.api.exceptionHandlers;

import fii.dip.api.exceptions.EmailAlreadyExistsException;
import fii.dip.api.exceptions.InvalidEmailFormatException;
import fii.dip.api.exceptions.ServiceUnavailableException;
import fii.dip.api.exceptions.UserNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"" + ex.getMessage() + "\"}");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"" + ex.getMessage() + "\"}");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJWTException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"" + ex.getMessage() + "\"}");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"" + ex.getMessage() + "\"}");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleInvalidEmailFormat(InvalidEmailFormatException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"" + ex.getMessage() + "\"}");
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<String> handleServiceUnavailable(ServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("{\"message\": \"" + ex.getMessage() + "\"}");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("{\"message\": \"" + ex.getMessage() + "\"}");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"" + ex.getMessage() + "\"}");
    }
}
