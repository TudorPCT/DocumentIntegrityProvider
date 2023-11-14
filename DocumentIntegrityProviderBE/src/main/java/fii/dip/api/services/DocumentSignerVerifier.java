package fii.dip.api.services;

import fii.dip.api.models.Document;

import java.security.*;
import java.util.Base64;

public class DocumentSignerVerifier {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private static DocumentSignerVerifier instance;

    private DocumentSignerVerifier() {
        // Initialize the private and public keys (Example: Generate key pair for RSA)
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // You can adjust the key size based on your security requirements
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static DocumentSignerVerifier getInstance() {
        if (instance == null) {
            instance = new DocumentSignerVerifier();
        }
        return instance;
    }

    // Method to sign a document and return the digital signature
    public String signDocument(Document document) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(document.getContent().getBytes());

            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to verify the integrity of a document using its digital signature
    public boolean verifyDocument(Document document, String digitalSignature) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(document.getContent().getBytes());

            byte[] signatureBytes = Base64.getDecoder().decode(digitalSignature);
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
