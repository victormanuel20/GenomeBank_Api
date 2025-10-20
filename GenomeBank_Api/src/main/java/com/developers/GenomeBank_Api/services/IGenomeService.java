package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.genome.*;
import com.developers.GenomeBank_Api.models.entities.Genome;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la administración de genomas.
 * Define las operaciones de negocio relacionadas con la entidad Genome.
 */
public interface IGenomeService {

    public CreateGenomeOutDTO createGenome(CreateGenomeInDTO createGenomeInDTO);
    public DeleteGenomeOutDTO deleteGenome(Long id);
    List<GenomeOutDTO> getGenomes(GetGenomesInDTO inDTO);
    GetGenomeByIdOutDTO getGenomeById(GetGenomeByIdInDTO inDTO);


}
