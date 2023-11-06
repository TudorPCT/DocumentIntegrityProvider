package fii.dip.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Document {
    private String title;
    private String content;

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
