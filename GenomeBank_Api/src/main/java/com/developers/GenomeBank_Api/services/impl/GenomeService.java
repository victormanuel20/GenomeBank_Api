package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.exceptions.GenomeNotFoundException;
import com.developers.GenomeBank_Api.models.dto.genome.CreateGenomeInDTO;
import com.developers.GenomeBank_Api.models.dto.genome.CreateGenomeOutDTO;
import com.developers.GenomeBank_Api.models.dto.genome.DeleteGenomeOutDTO;
import com.developers.GenomeBank_Api.models.entities.Genome;
import com.developers.GenomeBank_Api.models.entities.Species;
import com.developers.GenomeBank_Api.repositories.GenomeRepository;
import com.developers.GenomeBank_Api.repositories.SpeciesRepository;
import com.developers.GenomeBank_Api.services.IGenomeService;
import com.developers.GenomeBank_Api.services.ISpeciesService;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de genomas.
 * Proporciona la lógica de negocio para la gestión de genomas, incluyendo operaciones CRUD.
 * Utiliza GenomaRepository para el acceso a datos.
 */
@Service
public class GenomeService implements IGenomeService {

    private final GenomeRepository genomeRepository;
    private final ISpeciesService speciesService;
    private final SpeciesRepository speciesRepository;

    public GenomeService(GenomeRepository genomeRepository,
                         ISpeciesService speciesService,
                         SpeciesRepository speciesRepository) {
        this.genomeRepository = genomeRepository;
        this.speciesService = speciesService;
        this.speciesRepository = speciesRepository;
    }

    @Override
    public CreateGenomeOutDTO createGenome(CreateGenomeInDTO createGenomeInDTO) {

        CreateGenomeOutDTO outDTO = new CreateGenomeOutDTO();


        if (!this.speciesService.existsSpecies(createGenomeInDTO.getSpeciesId())) {
            outDTO.setSucess(false);
            outDTO.setErrorMessage("The specified species does not exist.");
            return outDTO;
        }


        Genome genome = new Genome();
        genome.setVersion(createGenomeInDTO.getVersion());


        Species species = speciesRepository.getReferenceById(createGenomeInDTO.getSpeciesId());
        genome.setSpecies(species);


        Genome savedGenome = this.genomeRepository.save(genome);

        outDTO.setSucess(true);
        outDTO.setId(savedGenome.getId());
        outDTO.setVersion(savedGenome.getVersion());
        outDTO.setSpeciesId(savedGenome.getSpecies().getId());

        return outDTO;
    }


    @Override
    public DeleteGenomeOutDTO deleteGenome(Long id) {
        DeleteGenomeOutDTO response = new DeleteGenomeOutDTO();
        response.setId(id);

        if (!genomeRepository.existsById(id)) {
            throw new GenomeNotFoundException(id);
        }

        genomeRepository.deleteById(id);

        response.setSucess(true);
        return response;

    }



}
