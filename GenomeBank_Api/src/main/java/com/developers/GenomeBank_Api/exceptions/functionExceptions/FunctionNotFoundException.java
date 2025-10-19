package com.developers.GenomeBank_Api.exceptions.functionExceptions;

public class FunctionNotFoundException extends RuntimeException {
    public FunctionNotFoundException(String message) {
        super(message);
    }
}
