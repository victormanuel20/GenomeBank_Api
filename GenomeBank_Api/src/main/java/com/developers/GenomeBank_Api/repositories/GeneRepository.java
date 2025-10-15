package com.developers.GenomeBank_Api.repositories;

import com.developers.GenomeBank_Api.models.entities.Gene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Gene.
 * Proporciona operaciones de persistencia para los genes almacenados en el sistema.
 */
@Repository
public interface GeneRepository extends JpaRepository<Gene, Long> {
}
