package com.developers.GenomeBank_Api.exceptions.genomeException;

public class InvalidSpeciesFilterException extends RuntimeException {

    public InvalidSpeciesFilterException(Long speciesId) {
        super("Cannot filter by species with ID " + speciesId + " because it does not exist");
    }
}