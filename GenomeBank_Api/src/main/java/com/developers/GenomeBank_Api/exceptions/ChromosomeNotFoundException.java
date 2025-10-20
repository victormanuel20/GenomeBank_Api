package com.developers.GenomeBank_Api.exceptions;

public class ChromosomeNotFoundException extends RuntimeException {
    public ChromosomeNotFoundException(String message) {
        super(message);
    }
}
