package com.developers.GenomeBank_Api.exceptions.functionExceptions;

/**
 * Excepción personalizada para errores al crear funciones biológicas.
 * Se lanza cuando ocurren problemas de validación o duplicados.
 */
public class FunctionNotCreatedException extends RuntimeException {
    public FunctionNotCreatedException(String message) {
        super(message);
    }
}
