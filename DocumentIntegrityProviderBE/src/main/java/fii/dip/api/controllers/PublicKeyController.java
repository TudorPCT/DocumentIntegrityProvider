package fii.dip.api.controllers;

import fii.dip.api.dtos.PublicKeyDto;
import fii.dip.api.services.interfaces.PublicKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public-key")
@RequiredArgsConstructor
public class PublicKeyController {
    private final PublicKeyService publicKeyService;

    @GetMapping("/{id}")
    public ResponseEntity<PublicKeyDto> retrieve(@PathVariable String id) {
        return new ResponseEntity<>(PublicKeyDto.builder()
                    .publicKey(publicKeyService.getPublicKeyById(id).getPublicKey())
                    .build(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody PublicKeyDto publicKeyDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();

        assert userId != null;

        publicKeyService.addUserPublicKey(publicKeyDto.getPublicKey(), userId);

        return ResponseEntity.ok(null);
    }
}
