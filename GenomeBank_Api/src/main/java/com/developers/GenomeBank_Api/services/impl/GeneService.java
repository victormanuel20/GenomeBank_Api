package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.models.dto.CreateGeneInDTO;
import com.developers.GenomeBank_Api.models.dto.AddGeneOutDTO;
import com.developers.GenomeBank_Api.models.dto.CreateGeneOutDTO;
import com.developers.GenomeBank_Api.models.entities.Chromosome;
import com.developers.GenomeBank_Api.models.entities.Gene;
import com.developers.GenomeBank_Api.repositories.GeneFunctionRepository;
import com.developers.GenomeBank_Api.repositories.GeneRepository;
import com.developers.GenomeBank_Api.services.IChromosomeService;
import com.developers.GenomeBank_Api.services.IFunctionService;
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
    private final GeneFunctionRepository geneFunctionRepository;
    private GeneRepository geneRepository;
    private final IFunctionService functionService;


    /**
     * Constructor que inyecta el repositorio de genes.

     * * @param geneRepository repository for genes
     * * @param geneFunctionRepository repository for gene-function associations
     * * @param chromosomeService service for chromosome operations
     * * @param functionService service for function operations
     */
    public GeneService(GeneRepository geneRepository, GeneFunctionRepository geneFunctionRepository,
                       IChromosomeService chromosomeService, IFunctionService functionService) {

        this.geneRepository = geneRepository;
        this.geneFunctionRepository = geneFunctionRepository;
        this.chromosomeService = chromosomeService;
        this.functionService = functionService;
    }

    @Override
    public CreateGeneOutDTO addGene(CreateGeneInDTO createGeneInDTO){

        CreateGeneOutDTO result = new CreateGeneOutDTO();
        if(!this.chromosomeService.existChromosome(createGeneInDTO.getChromosome())){
            result.setErrorMessage("The chromosome does not exist ");
            return result;
        }

            //Valida la hebra + / -
        if (!"+".equals(createGeneInDTO.getStrand()) && !"-".equals(createGeneInDTO.getStrand())) {
            result.setErrorMessage("Strand must be '+' or '-'");
            return result;
        }

        // Valida que el las posiciones no tenga incongruencias
        if (createGeneInDTO.getStartPosition() >= createGeneInDTO.getEndPosition()) {
            result.setErrorMessage("the start position must be less than end position");
            return result;
        }
        // valida que la longitud de la secuencia de ADN coincida con el rango de posiciones del gen en el cromosoma.
        if (createGeneInDTO.getSequence() != null && !createGeneInDTO.getSequence().isEmpty()) {
            long expectedLength = createGeneInDTO.getEndPosition() - createGeneInDTO.getStartPosition();
            if (createGeneInDTO.getSequence().length() != expectedLength) {
                result.setMensajeError("the sequence length must match the position range");
                return result;
            }
        }

        Gene gene = new Gene();
        gene.setSymbol(createGeneInDTO.getSymbol());
        gene.setStartPosition(createGeneInDTO.getStartPosition());
        gene.setEndPosition(createGeneInDTO.getEndPosition());
        gene.setStrand(createGeneInDTO.getStrand());
        gene.setSequence(createGeneInDTO.getSequence());

        Chromosome chromosome = new Chromosome();
        chromosome.setId(createGeneInDTO.getChromosomeId());
        gene.setChromosome(chromosome);

        Gene savedGene = geneRepository.save(gene);

        if (savedGene.getId() != null) {
            result.isSuccess(true);
            result.setGeneId(savedGene.getId());
        }

        return result;
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
