package com.developers.GenomeBank_Api.repositories;

import com.developers.GenomeBank_Api.models.entities.GeneFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad GeneFunction.
 * Representa la relación entre genes y funciones, permitiendo consultar y modificar
 * las asociaciones entre ambas entidades.
 */
@Repository
public interface GeneFunctionRepository extends JpaRepository<GeneFunction, Long> {
}
