package com.developers.GenomeBank_Api.exceptions.genomeException;

public class MissingFieldsException extends RuntimeException {
    public MissingFieldsException(String message) {
        super(message);
    }
}
