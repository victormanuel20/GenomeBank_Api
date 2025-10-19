package com.developers.GenomeBank_Api.exceptions.functionExceptions;

/**
 * Excepción lanzada cuando se intenta eliminar una función
 * que está asociada a uno o varios genes en la tabla intermedia.
 */
public class FunctionInUseException extends RuntimeException {
    public FunctionInUseException(String message) {
        super(message);
    }
}
