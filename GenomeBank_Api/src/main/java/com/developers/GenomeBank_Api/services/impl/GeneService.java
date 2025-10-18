package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.models.dto.CreateGeneInDTO;
import com.developers.GenomeBank_Api.models.dto.AddGeneOutDTO;
import com.developers.GenomeBank_Api.models.entities.Chromosome;
import com.developers.GenomeBank_Api.models.entities.Gene;
import com.developers.GenomeBank_Api.repositories.GeneRepository;
import com.developers.GenomeBank_Api.services.IChromosomeService;
import com.developers.GenomeBank_Api.services.IGeneService;
import org.springframework.stereotype.Service;

import java.util.List;
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
        this.chromosomeService = new ChromosomeService();
    }

    @Override
    public AddGeneOutDTO addGene(CreateGeneInDTO createGeneInDTO){

        AddGeneOutDTO addGeneOutDTO = new AddGeneOutDTO();
        if(!this.chromosomeService.existChromosome(createGeneInDTO.getChromosome())){
            addGeneOutDTO.setErrorMessage("Dosent exist chromosome");
            return addGeneOutDTO;
        }

        Gene gene= new Gene();
        gene.setSymbol(createGeneInDTO.getSymbol());
        gene.setStartPosition(createGeneInDTO.getStartPosition());
        gene.setEndPosition(createGeneInDTO.getEndPosition());
        gene.setStrand(createGeneInDTO.getStrand());
        gene.setSequence(createGeneInDTO.getSequence());
        Chromosome chromosome= new Chromosome();
        chromosome.setId(createGeneInDTO.getChromosome());
        gene.setChromosome(chromosome);
        Gene geneCreated =this.geneRepository.save(gene);

        if (geneCreated.getId() != null) {
            addGeneOutDTO.setSuccess(true);
        }
        return addGeneOutDTO;
    }


    @Override
    public Optional<Gene> getGene(Long id) {

        return this.geneRepository.findById(id);

    }


    @Override
    public List<Gene> getGenes(){

        return this.geneRepository.findAll();
    }
}
