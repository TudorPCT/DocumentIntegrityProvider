package fii.dip.api.mappers;

import fii.dip.api.models.Document;

public class DocumentBuilder {
    private String title;
    private String content;

    public DocumentBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public DocumentBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public Document build() {
        return new Document(title, content);
    }
}
