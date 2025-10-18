package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.CreateGeneInDTO;
import com.developers.GenomeBank_Api.models.dto.CreateGeneOutDTO;
import com.developers.GenomeBank_Api.models.dto.GeneOutDTO;
import com.developers.GenomeBank_Api.models.dto.GeneWithSequenceOutDTO;


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
}
