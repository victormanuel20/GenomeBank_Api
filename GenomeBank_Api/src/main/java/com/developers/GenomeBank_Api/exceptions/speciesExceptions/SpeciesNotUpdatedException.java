package com.developers.GenomeBank_Api.exceptions.speciesExceptions;

/**
 * Excepción personalizada cuando hay un error
 * al actualizar la especie
 */
public class SpeciesNotUpdatedException extends RuntimeException{
    public SpeciesNotUpdatedException(String message) {
        super(message);
    }
}
