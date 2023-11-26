package fii.dip.api.repositories;

import fii.dip.api.models.PublicKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicKeyRepository  extends JpaRepository<PublicKey, String> {
    Optional<PublicKey> findByUserId(String UserId);
}
