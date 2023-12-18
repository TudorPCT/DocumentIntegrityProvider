package fii.dip.api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentTest {

    @Test
    public void testDocumentCreation() {
        String title = "Test Title";
        String content = "Test Content";
        Document document = new Document(title, content);

        assertEquals(title, document.getTitle());
        assertEquals(content, document.getContent());
    }

    @Test
    public void testSetTitle() {
        String title = "New Title";
        Document document = new Document("Old Title", "Content");

        document.setTitle(title);

        assertEquals(title, document.getTitle());
    }

    @Test
    public void testSetContent() {
        String content = "New Content";
        Document document = new Document("Title", "Old Content");

        document.setContent(content);

        assertEquals(content, document.getContent());
    }
}
