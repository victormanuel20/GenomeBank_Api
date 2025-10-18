package com.developers.GenomeBank_Api.exceptions;

/**
 * Excepción personalizada cuando hay un error
 * al crear una especie
 */
public class SpeciesNotCreatedException extends RuntimeException{
    public SpeciesNotCreatedException(String message) {
        super(message);
    }
}
