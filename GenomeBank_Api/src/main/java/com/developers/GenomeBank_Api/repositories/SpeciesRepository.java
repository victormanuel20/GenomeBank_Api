package com.developers.GenomeBank_Api.repositories;

import com.developers.GenomeBank_Api.models.dto.speciesDtos.CreateSpeciesOutDTO;
import com.developers.GenomeBank_Api.models.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Species.
 * Permite realizar operaciones CRUD sobre las especies almacenadas en la base de datos.
 */
@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {
    Optional<Species> findByScientificName(String scientificName);
    Optional<Species> findByCommonName(String commonName);
}
