package com.louishhy.paperlinkbackend.exception.crossref;

public class CrossrefServerErrorException extends RuntimeException {
    public CrossrefServerErrorException(String message) {
        super(message);
    }
}
