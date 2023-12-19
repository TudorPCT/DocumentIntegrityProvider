package fii.dip.api.services.implementations;

import fii.dip.api.exceptions.PublicKeyNotFoundException;
import fii.dip.api.exceptions.UserNotFoundException;
import fii.dip.api.models.PublicKey;
import fii.dip.api.models.User;
import fii.dip.api.repositories.PublicKeyRepository;
import fii.dip.api.repositories.UserRepository;
import fii.dip.api.services.interfaces.PublicKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicKeyServiceImpl implements PublicKeyService {

    private final UserRepository userRepository;
    private final PublicKeyRepository publicKeyRepository;

    @Override
    public PublicKey getPublicKeyById(String id) {
        return publicKeyRepository.findById(id).orElseThrow(() -> new PublicKeyNotFoundException("Public key not found"));
    }

    @Override
    public PublicKey addUserPublicKey(String publicKey, String userId) {

        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        PublicKey publicKeyEntity = new PublicKey(publicKey, owner);

        publicKeyEntity = publicKeyRepository.save(publicKeyEntity);

        return publicKeyEntity;
    }
}
