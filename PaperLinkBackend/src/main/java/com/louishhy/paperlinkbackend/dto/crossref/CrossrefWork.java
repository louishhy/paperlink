package com.louishhy.paperlinkbackend.dto.crossref;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrossrefWork {
    @JsonProperty("DOI")
    private String doi;

    private List<String> title;  // In JSON: "title": ["The paper title"]

    private List<Author> author; // In JSON: "author": [{"given": "John", "family": "Doe"}]

    @JsonProperty("abstract")   // JSON field is "abstract"
    private String abstract_;

    @JsonProperty("URL")         // JSON field is "URL"
    private String url;

    @JsonProperty("container-title")  // JSON field is "container-title"
    private List<String> containerTitle;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Author {
        private String given;
        private String family;
    }
}