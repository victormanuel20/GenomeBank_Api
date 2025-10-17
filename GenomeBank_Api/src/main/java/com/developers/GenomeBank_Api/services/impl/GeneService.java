package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.models.dto.AddGeneInDTO;
import com.developers.GenomeBank_Api.models.dto.AddGeneOutDTO;
import com.developers.GenomeBank_Api.models.entities.Chromosome;
import com.developers.GenomeBank_Api.models.entities.Gene;
import com.developers.GenomeBank_Api.repositories.GeneRepository;
import com.developers.GenomeBank_Api.services.IChromosomeService;
import com.developers.GenomeBank_Api.services.IGeneService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementación del servicio de genes.
 * Proporciona la lógica de negocio para la gestión de genes, incluyendo operaciones CRUD.
 * Utiliza GenRepository para el acceso a datos.
 */
@Service
public class GeneService implements IGeneService {

    private final IChromosomeService chromosomeService;
    private GeneRepository geneRepository;

    /**
     * Constructor que inyecta el repositorio de autores.
     * @param geneRepository repositorio de autores
     */
    public GeneService(GeneRepository geneRepository) {
        this.geneRepository = geneRepository;
    }

    @Override
    public AddGeneOutDTO addGene(AddGeneInDTO addGeneInDTO){

        AddGeneOutDTO addGeneOutDTO = new AddGeneOutDTO();
        if(!this.chromosomeService.existChromosome(addGeneInDTO.getChromosome())){
            addGeneOutDTO.setErrorMessage("Dosent exist chromosome");
            return addGeneOutDTO;
        }

        Gene gene= new Gene();
        gene.setSymbol(addGeneInDTO.getSymbol());
        gene.setStartPosition(addGeneInDTO.getStartPosition());
        gene.setEndPosition(addGeneInDTO.getEndPosition());
        gene.setStrand(addGeneInDTO.getStrand());
        gene.setSequence(addGeneInDTO.getSequence());
        Chromosome chromosome= new Chromosome();
        chromosome.setId(addGeneInDTO.getChromosome());
        gene.setChromosome(chromosome);
        Gene geneCreated =this.geneRepository.save(gene);

        if (geneCreated.getId() != null) {
            Set<>
        }
    }


    @Override
    public Optional<Gene> getGene(Long id) {return this.geneRepository.findById(id);

    }



}
