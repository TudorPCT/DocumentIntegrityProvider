package fii.dip.api.services.interfaces;

import fii.dip.api.models.PublicKey;

public interface PublicKeyService {
    PublicKey getPublicKeyById(String id);
    PublicKey addUserPublicKey(String publicKey, String userId);
}
