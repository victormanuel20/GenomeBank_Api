package com.developers.GenomeBank_Api.exceptions.genomeException;

public class InvalidSpeciesForGenomeException extends RuntimeException {

    public InvalidSpeciesForGenomeException(Long speciesId) {
        super("Cannot update genome: Species with ID " + speciesId + " does not exist");
    }
}
