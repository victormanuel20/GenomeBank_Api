package com.developers.GenomeBank_Api.exceptions.speciesExceptions;

/**
 * Excepción personalizada cuando hay un error
 * al buscar una especie
 */
public class SpeciesNotFoundException extends RuntimeException {
    public SpeciesNotFoundException(String message) {
        super(message);
    }
}
