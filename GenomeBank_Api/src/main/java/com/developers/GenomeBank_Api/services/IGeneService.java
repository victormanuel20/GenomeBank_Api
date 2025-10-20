package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.*;
import com.developers.GenomeBank_Api.models.dto.geneDtos.*;


import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la administración de genes.
 * Define las operaciones de negocio que permiten gestionar los objetos Gene.
 */
public interface IGeneService {

    CreateGeneOutDTO createGene(CreateGeneInDTO createGeneInDTO);

    Optional<GeneWithSequenceOutDTO> getGeneById(Long id);

    List<GeneOutDTO> getAllGenes(Long chromosomeId, String symbol, Integer start, Integer end);

    Optional<GeneOutDTO> updateGene(Long id, UpdateGeneInDTO updateGeneInDTO);

    boolean deleteGene(Long id);

    Optional<String> getGeneSequence(Long id);

    Optional<GeneOutDTO> updateGeneSequence(Long id, UpdateGeneSequenceInDTO updateGeneSequenceInDTO);

    // ===== MÉTODOS PARA RELACIÓN MUCHOS-A-MUCHOS CON FUNCIONES =====

    List<GeneFunctionOutDTO> getGeneFunctions(Long geneId);


}
