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

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto) {

        userService.register(newUserDto);

        return ResponseEntity.ok("{\"message\": \"User registered successfully\"}");
    }

}
