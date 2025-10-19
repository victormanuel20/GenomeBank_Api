package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.models.dto.*;

import java.util.stream.Collectors;

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
     * Constructor que inyecta el repositorio de genes
     * * @param geneRepository repositorio para genes
     * * @param geneFunctionRepository repositorio para las asociaciones de gen funcion for
     * * @param chromosomeService para las opreciones de cromosomas
     * * @param functionService servicio para operaciones de funcion
     */
    public GeneService(GeneRepository geneRepository, GeneFunctionRepository geneFunctionRepository,
                       IChromosomeService chromosomeService, IFunctionService functionService) {

        this.geneRepository = geneRepository;
        this.geneFunctionRepository = geneFunctionRepository;
        this.chromosomeService = chromosomeService;
        this.functionService = functionService;
    }

    @Override
    public CreateGeneOutDTO createGene(CreateGeneInDTO createGeneInDTO) {

        CreateGeneOutDTO result = new CreateGeneOutDTO();
        if (!this.chromosomeService.existChromosome(createGeneInDTO.getChromosome())) {
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
                result.setErrorMessage("the sequence length must match the position range");
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
            result.setSuccess(true);
            result.setGeneId(savedGene.getId());
        }

        return result;
    }


    /**
     * Obtiene un gen especifico por el id
     * @param id identificador del gen
     * @return Optional con el gen o vacio
     */
    @Override
    public Optional <GeneWithSequenceOutDTO> getGeneById(Long id) {
        return geneRepository.findById(id)
                .map(this::convertToGeneWithSequenceOutDTO);
    }


    /**
     * Obtiene todos los genes con los ftilros adicionales
     *
     * @param chromosomeId filtrado  por chromosome
     * @param symbol        filtrado  porsymbol
     * @param start         filtrado  por posicion inicial
     * @param end          filtrado  por posicion final
     * @return Lista del todos los genes
     */
    @Override
    public List<GeneOutDTO> getAllGenes(Long chromosomeId, String symbol, Integer start, Integer end) {
        List<Gene> genes;

        if (chromosomeId != null || symbol != null || start != null || end != null) {
            genes = geneRepository.findByFilters(chromosomeId, symbol, start, end);
        } else {
            genes = geneRepository.findAll();
        }

        return genes.stream()
                .map(this::convertToGeneOutDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un gen
     * @param id identidficador del gen
     * @param updateGeneInDTO informacion a actualizar
     * @return Optional wcon el gen actualizado o vacio si no pudo
     */
    @Override

    public Optional<GeneOutDTO> updateGene(Long id, UpdateGeneInDTO updateGeneInDTO) {
        return geneRepository.findById(id)
                .map(existingGene -> {
                    if (updateGeneInDTO.getSymbol() != null) {
                        existingGene.setSymbol(updateGeneInDTO.getSymbol());
                    }
                    if (updateGeneInDTO.getStartPosition() != null) {
                        existingGene.setStartPosition(updateGeneInDTO.getStartPosition());
                    }
                    if (updateGeneInDTO.getEndPosition() != null) {
                        existingGene.setEndPosition(updateGeneInDTO.getEndPosition());
                    }
                    if (updateGeneInDTO.getStrand() != null) {
                        if (!"+".equals(updateGeneInDTO.getStrand()) && !"-".equals(updateGeneInDTO.getStrand())) {
                            throw new IllegalArgumentException("Strand must be '+' or '-'");
                        }
                        existingGene.setStrand(updateGeneInDTO.getStrand());
                    }
                    if (updateGeneInDTO.getSequence() != null) {
                        existingGene.setSequence(updateGeneInDTO.getSequence());
                    }

                    Gene savedGene = geneRepository.save(existingGene);
                    return convertToGeneOutDTO(savedGene);
                });
    }

    /**
     * Elimina un gen
     * @param id identidficador del gen
     * @return true si se elimina false si no
     */
    @Override

    public boolean deleteGene(Long id) {
        if (geneRepository.existsById(id)) {
            geneRepository.deleteById(id);
            return true;
        }
        return false;
    }


    /**
     * Obtiene la secuencia de un gen
     * @param id gen identificador
     * @return Optional con la secuencia o vacio si no la encuetnra
     */
    @Override
    public Optional<String> getGeneSequence(Long id) {
        return geneRepository.findById(id)
                .map(Gene::getSequence);
    }

    /**
     * Actualiza la secuencia de un gen
     * @param id identificador del gen
     * @param updateGeneSequenceInDTO nueva secuencia
     * @return Optional con el gen actualizado o vacio
     */
    @Override
    public Optional<GeneOutDTO> updateGeneSequence(Long id, UpdateGeneSequenceInDTO updateGeneSequenceInDTO) {
        return geneRepository.findById(id)
                .map(existingGene -> {
                    long expectedLength = existingGene.getEndPosition() - existingGene.getStartPosition();
                    if (updateGeneSequenceInDTO.getSequence().length() != expectedLength) {
                        throw new IllegalArgumentException("Sequence length must match the gene position range");
                    }
                    existingGene.setSequence(updateGeneSequenceInDTO.getSequence());
                    Gene savedGene = geneRepository.save(existingGene);
                    return convertToGeneOutDTO(savedGene);
                });
    }





    // Convierte la entidad en un DTO
    private GeneOutDTO convertToGeneOutDTO(Gene gene) {
        GeneOutDTO dto = new GeneOutDTO();
        dto.setId(gene.getId());
        dto.setSymbol(gene.getSymbol());
        dto.setStartPosition(gene.getStartPosition());
        dto.setEndPosition(gene.getEndPosition());
        dto.setStrand(gene.getStrand());
        dto.setChromosomeId(gene.getChromosome().getId());
        if (gene.getChromosome().getName() != null) {
            dto.setChromosomeName(gene.getChromosome().getName());
        }
        return dto;
    }
    private GeneWithSequenceOutDTO convertToGeneWithSequenceOutDTO(Gene gene) {
        GeneWithSequenceOutDTO dto = new GeneWithSequenceOutDTO();
        dto.setId(gene.getId());
        dto.setSymbol(gene.getSymbol());
        dto.setStartPosition(gene.getStartPosition());
        dto.setEndPosition(gene.getEndPosition());
        dto.setStrand(gene.getStrand());
        dto.setChromosomeId(gene.getChromosome().getId());
        if (gene.getChromosome().getName() != null) {
            dto.setChromosomeName(gene.getChromosome().getName());
        }
        dto.setSequence(gene.getSequence());
        return dto;
    }

}