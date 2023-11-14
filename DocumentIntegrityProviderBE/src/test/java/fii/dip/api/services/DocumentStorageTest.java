package fii.dip.api.services;

import fii.dip.api.models.Document;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DocumentStorageTest {

    @Test
    public void testSaveAndGetDocumentByTitle() {
        // Create an instance of DocumentStorage
        DocumentStorage documentStorage = new DocumentStorage();

        // Create a sample document
        Document document = new Document("Sample Document", "This is the content of the document.");

        // Save the document
        documentStorage.saveDocument(document);

        // Retrieve the document by title
        Document retrievedDocument = documentStorage.getDocumentByTitle("Sample Document");

        // Check if the retrieved document is not null
        assertNotNull(retrievedDocument, "Retrieved document should not be null");

        // Check if the content of the retrieved document matches the original content
        assertEquals("This is the content of the document.", retrievedDocument.getContent(),
                "Content of the retrieved document should match the original content");
    }

    @Test
    public void testGetAllDocuments() {
        // Create an instance of DocumentStorage
        DocumentStorage documentStorage = new DocumentStorage();

        // Create sample documents
        Document document1 = new Document("Document 1", "Content 1");
        Document document2 = new Document("Document 2", "Content 2");

        // Save the documents
        documentStorage.saveDocument(document1);
        documentStorage.saveDocument(document2);

        // Retrieve all documents
        List<Document> allDocuments = documentStorage.getAllDocuments();

        // Check if the list of all documents is not null
        assertNotNull(allDocuments, "List of all documents should not be null");

        // Check if the list contains the expected number of documents
        assertEquals(2, allDocuments.size(), "List of all documents should contain 2 documents");
    }
}