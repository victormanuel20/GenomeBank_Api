package com.developers.GenomeBank_Api.repositories;

import com.developers.GenomeBank_Api.models.entities.GeneFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
/**
 * Repositorio JPA para la entidad GeneFunction.
 * Representa la relación entre genes y funciones, permitiendo consultar y modificar
 * las asociaciones entre ambas entidades.
 */
@Repository
public interface GeneFunctionRepository extends JpaRepository<GeneFunction, Long> {

    List<GeneFunction> findByGeneId(Long geneId);

    List<GeneFunction> findByFunctionId(Long functionId);

    @Query("SELECT gf FROM GeneFunction gf WHERE gf.gene.id = :geneId AND gf.function.id = :functionId")
    Optional<GeneFunction> findByGeneIdAndFunctionId(
            @Param("geneId") Long geneId,
            @Param("functionId") Long functionId
    );

    void deleteByGeneIdAndFunctionId(Long geneId, Long functionId);
}
