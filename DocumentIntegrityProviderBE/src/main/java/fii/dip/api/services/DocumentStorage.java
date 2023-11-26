package fii.dip.api.services;

import fii.dip.api.models.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentStorage {
    private final List<Document> documentList = new ArrayList<>();

    // Save a document
    public void saveDocument(Document document) {
        documentList.add(document);
    }

    // Retrieve a document by its title
    public Document getDocumentByTitle(String title) {
        for (Document document : documentList) {
            if (document.getTitle().equals(title)) {
                return document;
            }
        }
        return null; // Document not found
    }

    // Retrieve a list of all documents
    public List<Document> getAllDocuments() {
        return new ArrayList<>(documentList);
    }
}