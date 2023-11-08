package fii.dip.api.controllers;

import fii.dip.api.services.DocumentSignerVerifier;
import fii.dip.api.services.DocumentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentSignerVerifier documentSignerVerifier = DocumentSignerVerifier.getInstance();
    private final DocumentStorage documentStorage;

    @PostMapping("sign")
    public ResponseEntity<Void> sign() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("validate")
    public ResponseEntity<Void> validate() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("save")
    public ResponseEntity<Void> save() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("retrieve")
    public ResponseEntity<Void> retrieve() {
        return ResponseEntity.ok(null);
    }


}
