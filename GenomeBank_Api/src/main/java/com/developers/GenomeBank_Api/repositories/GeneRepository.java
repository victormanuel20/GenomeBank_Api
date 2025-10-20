package com.developers.GenomeBank_Api.repositories;

import com.developers.GenomeBank_Api.models.entities.Gene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
/**
 * Repositorio JPA para la entidad Gene.
 * Proporciona operaciones de persistencia para los genes almacenados en el sistema.
 */
@Repository
public interface GeneRepository extends JpaRepository<Gene, Long> {



    @Query("SELECT g FROM Gene g WHERE " +
            "(:chromosomeId IS NULL OR g.chromosome.id = :chromosomeId) AND " +
            "(:symbol IS NULL OR g.symbol LIKE %:symbol%) AND " +
            "(:start IS NULL OR g.startPosition >= :start) AND " +
            "(:end IS NULL OR g.endPosition <= :end)")
    List<Gene> findByFilters(
            @Param("chromosomeId") Long chromosomeId,
            @Param("symbol") String symbol,
            @Param("start") Integer start,
            @Param("end") Integer end
    );
}
