package fii.dip.api.controllers;

import fii.dip.api.dtos.NewUserDto;
import fii.dip.api.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("validate")
    public ResponseEntity<Void> validate() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
