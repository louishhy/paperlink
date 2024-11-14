package com.louishhy.paperlinkbackend.exception.graphservice;

public class GraphNotFoundException extends RuntimeException {
    public GraphNotFoundException(String message) {
        super(message);
    }
}
