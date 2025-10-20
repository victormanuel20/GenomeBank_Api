package com.developers.GenomeBank_Api.exceptions.genomeException;

/**
 * Custom exception thrown when a genome with a specific ID does not exist.
 */
public class GenomeNotFoundException extends RuntimeException {
    public GenomeNotFoundException(Long id) {
        super("Genome not found with ID: " + id);
    }
}
