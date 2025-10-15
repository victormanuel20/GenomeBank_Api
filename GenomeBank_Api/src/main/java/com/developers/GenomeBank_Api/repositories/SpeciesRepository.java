package com.developers.GenomeBank_Api.repositories;

import com.developers.GenomeBank_Api.models.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Species.
 * Permite realizar operaciones CRUD sobre las especies almacenadas en la base de datos.
 */
@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {
}
