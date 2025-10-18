package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.genome.CreateGenomeInDTO;
import com.developers.GenomeBank_Api.models.dto.genome.CreateGenomeOutDTO;
import com.developers.GenomeBank_Api.models.entities.Genome;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la administración de genomas.
 * Define las operaciones de negocio relacionadas con la entidad Genome.
 */
public interface IGenomeService {
    // Create a new genome (ADMIN only)
    public CreateGenomeOutDTO createGenome(CreateGenomeInDTO createGenomeInDTO);
    public boolean deleteGenome(Long id);

    /*
    // Retrieve all genomes or filter by species
    List<Genome> getAllGenomes(Long speciesId);

    // Get a genome by its ID
    public Optional<Genome> getGenomeById(Long id);

    // Update an existing genome
    public Optional<Genome> updateGenome(Long id, Genome genome);

    // Delete a genome by ID
    boolean deleteGenome(Long id);


     */

}
