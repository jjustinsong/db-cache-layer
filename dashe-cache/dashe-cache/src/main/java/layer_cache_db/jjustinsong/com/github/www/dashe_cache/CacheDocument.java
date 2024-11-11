package com.yourpackage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cacheLayer")
public class CacheDocument {

    @Id
    private String id;
    private String data;

    // Constructors
    public CacheDocument() {
    }

    public CacheDocument(String id, String data) {
        this.id = id;
        this.data = data;
    }

    // Getters and Setters
    // ...
}
