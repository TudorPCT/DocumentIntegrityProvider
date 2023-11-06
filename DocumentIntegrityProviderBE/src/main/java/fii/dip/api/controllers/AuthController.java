package fii.dip.api.controllers;

import fii.dip.api.services.implementations.UserServiceImpl;
import fii.dip.api.dtos.NewUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;

    @GetMapping("validate")
    public ResponseEntity<Void> validate() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
