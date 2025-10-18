package com.developers.GenomeBank_Api.repositories;

import com.developers.GenomeBank_Api.models.entities.Genome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Genome.
 * Facilita las operaciones CRUD sobre los genomas registrados en el sistema.
 */
@Repository
public interface GenomeRepository extends JpaRepository<Genome, Long> {

    /**
     * Finds all genomes that belong to a specific species.
     * @param speciesId ID of the species
     * @return list of genomes that belong to that species
     */
    List<Genome> findBySpeciesId(Long speciesId);

}
