package fii.dip.api.services;

import fii.dip.api.models.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentSignerVerifierTest {

    @Test
    public void testSignAndVerifyDocument() {
        // Create an instance of DocumentSignerVerifier
        DocumentSignerVerifier signerVerifier = DocumentSignerVerifier.getInstance();

        // Create a sample document
        Document document = new Document("Sample Document", "This is the content of the document.");

        // Sign the document
        String digitalSignature = signerVerifier.signDocument(document);

        // Verify the document with the correct signature
        boolean isValid = signerVerifier.verifyDocument(document, digitalSignature);
        assertTrue(isValid, "Document verification should succeed with the correct signature");

        // Modify the document content to simulate tampering
        document.setContent("Modified content of the document.");

        // Verify the document with the correct signature (after modification)
        isValid = signerVerifier.verifyDocument(document, digitalSignature);
        assertFalse(isValid, "Document verification should fail after content modification");

        // Verify the document with an incorrect signature
        isValid = signerVerifier.verifyDocument(document, "IncorrectSignature");
        assertFalse(isValid, "Document verification should fail with an incorrect signature");
    }
}