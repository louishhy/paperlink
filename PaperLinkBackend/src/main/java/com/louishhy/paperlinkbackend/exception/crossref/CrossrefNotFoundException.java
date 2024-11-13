package com.louishhy.paperlinkbackend.exception.crossref;

public class CrossrefNotFoundException extends RuntimeException {
    public CrossrefNotFoundException(String message) {
        super(message);
    }
}
