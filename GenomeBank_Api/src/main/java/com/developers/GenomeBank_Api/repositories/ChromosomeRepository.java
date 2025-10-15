package com.developers.GenomeBank_Api.repositories;

import com.developers.GenomeBank_Api.models.entities.Chromosome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Chromosome.
 * Permite realizar operaciones CRUD sobre los cromosomas en la base de datos.
 * Hereda los métodos predefinidos de JpaRepository.
 */
@Repository
public interface ChromosomeRepository extends JpaRepository<Chromosome, Long> {
}
