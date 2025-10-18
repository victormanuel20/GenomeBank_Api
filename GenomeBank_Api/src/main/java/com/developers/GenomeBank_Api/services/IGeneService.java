package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.CreateGeneInDTO;
import com.developers.GenomeBank_Api.models.entities.Gene;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la administración de genes.
 * Define las operaciones de negocio que permiten gestionar los objetos Gene.
 */
public interface IGeneService {


    public List<Gene> getGenes();
    public Optional<Gene> getGene(Long id);
    public Gene addGene(CreateGeneInDTO createGeneInDTO);


}
