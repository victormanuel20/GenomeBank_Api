package com.developers.GenomeBank_Api.exceptions.geneExeptions;

public class GenNotFoundException extends RuntimeException {
    public GenNotFoundException(String message) {
        super(message);
    }
}
