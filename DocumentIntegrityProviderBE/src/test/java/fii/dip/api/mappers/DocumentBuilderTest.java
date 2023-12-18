package fii.dip.api.mappers;

import fii.dip.api.models.Document;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DocumentBuilderTest {

    @Test
    public void testDocumentBuilder() {
        String title = "Test Title";
        String content = "Test Content";

        Document document = new DocumentBuilder()
                .setTitle(title)
                .setContent(content)
                .build();

        assertEquals(title, document.getTitle());
        assertEquals(content, document.getContent());
    }

    @Test
    public void testDocumentBuilderWithDefaultValues() {
        Document document = new DocumentBuilder().build();

        // Assuming default values are set in the Document constructor
        assertNull(document.getTitle());
        assertNull(document.getContent());
    }

    @Test
    public void testDocumentBuilderChaining() {
        String title = "Test Title";
        String content = "Test Content";

        Document document = new DocumentBuilder()
                .setTitle(title)
                .setContent(content)
                .setTitle("Updated Title")  // Testing method chaining
                .build();

        assertEquals("Updated Title", document.getTitle());
        assertEquals(content, document.getContent());
    }
}
