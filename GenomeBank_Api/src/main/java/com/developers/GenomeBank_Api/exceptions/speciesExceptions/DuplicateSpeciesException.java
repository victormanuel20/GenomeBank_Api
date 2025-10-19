package com.developers.GenomeBank_Api.exceptions.speciesExceptions;

/**
 * Clase para la excepción de especies duplicadas
 */
public class DuplicateSpeciesException extends RuntimeException{
    public DuplicateSpeciesException(String message){
        super(message);
    }
}
