package fii.dip.api.services.interfaces;

import fii.dip.api.models.PublicKey;

public interface PublicKeyService {
    PublicKey getPublicKeyByUserId(String userId);
    PublicKey addUserPublicKey(String publicKey, String userId);
}
