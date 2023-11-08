package fii.dip.api.services;

import fii.dip.api.models.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentStorage {
    // Save a document
    public void saveDocument(Document document) {
    }

    // Retrieve a document by its title
    public Document getDocumentByTitle(String title) {
        return null;
    }

    // Retrieve a list of all documents
    public List<Document> getAllDocuments() {
        return new ArrayList<>();
    }
}
