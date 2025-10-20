package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.exceptions.functionExceptions.FunctionNotFoundException;
import com.developers.GenomeBank_Api.exceptions.geneExeptions.GenNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

import com.developers.GenomeBank_Api.models.dto.geneDtos.*;
import com.developers.GenomeBank_Api.models.entities.Chromosome;
import com.developers.GenomeBank_Api.models.entities.Function;
import com.developers.GenomeBank_Api.models.entities.Gene;
import com.developers.GenomeBank_Api.models.entities.GeneFunction;
import com.developers.GenomeBank_Api.repositories.ChromosomeRepository;
import com.developers.GenomeBank_Api.repositories.GeneFunctionRepository;
import com.developers.GenomeBank_Api.repositories.GeneRepository;
import com.developers.GenomeBank_Api.services.IChromosomeService;
import com.developers.GenomeBank_Api.services.IFunctionService;
import com.developers.GenomeBank_Api.services.IGeneFunctionService;
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
    private final ChromosomeRepository chromosomeRepository;
    private GeneRepository geneRepository;
    private final IFunctionService functionService;
    private final IGeneFunctionService geneFunctionService;

    /**
     * Constructor que inyecta el repositorio de genes
     * * @param geneRepository repositorio para genes
     * * @param geneFunctionRepository repositorio para las asociaciones de gen funcion for
     * * @param chromosomeService para las opreciones de cromosomas
     * * @param functionService servicio para operaciones de funcion
     */
    public GeneService(GeneRepository geneRepository, GeneFunctionRepository geneFunctionRepository,
                       IChromosomeService chromosomeService, IFunctionService functionService, ChromosomeRepository chromosomeRepository, IGeneFunctionService geneFunctionService) {
    this.geneFunctionService = geneFunctionService;
        this.geneRepository = geneRepository;
        this.geneFunctionRepository = geneFunctionRepository;
        this.chromosomeService = chromosomeService;
        this.functionService = functionService;
        this.chromosomeRepository = chromosomeRepository;
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

        if (!validateExistFunction(createGeneInDTO.getFunctions())) {
            result.setErrorMessage("Alguna de las funciones ingresadas no existe");
            return result;
        }
        Gene gene = new Gene();
        gene.setSymbol(createGeneInDTO.getSymbol());
        gene.setStartPosition(createGeneInDTO.getStartPosition());
        gene.setEndPosition(createGeneInDTO.getEndPosition());
        gene.setStrand(createGeneInDTO.getStrand());
        gene.setSequence(createGeneInDTO.getSequence());


        Chromosome chromosome = chromosomeRepository.getReferenceById(createGeneInDTO.getChromosome());
        gene.setChromosome(chromosome);
        Gene savedGene = geneRepository.save(gene);


        if (savedGene.getId() != null) {
            Set<GeneFunction> geneFunctions = createGeneInDTO.getFunctions().stream().map(geneFunctionDTO -> {
                return geneFuncionDTOToEntity(geneFunctionDTO, savedGene.getId());
            }).collect(Collectors.toSet());
            savedGene.setGeneFunctions(geneFunctions);
            this.geneFunctionService.createGeneFunctionBatch(geneFunctions);
            result.setSucess(true);

        }

        return result;
    }


    private boolean validateExistFunction(Set<GeneFunctionDTO> funciones) {
        int count = 0;
        for (GeneFunctionDTO funcion : funciones) {
            if (!this.functionService.existsById(funcion.getFunction())) {
                count++;
            }
        }
        return count == 0;
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
            if (genes.isEmpty()) {
                throw new GenNotFoundException("Gene not found");
            }
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


    /**
     * Obtiene todas las funciones asociadas a un gen específico
     * @param geneId identificador del gen
     * @return lista de funciones asociadas al gen
     */
    @Override
    public List<GeneFunctionOutDTO> getGeneFunctions(Long geneId) {
        if (!geneRepository.existsById(geneId)) {
            throw new GenNotFoundException("Gene with ID " + geneId + " not found");
        }

        List<GeneFunction> geneFunctions = geneFunctionRepository.findByGeneId(geneId);
        if (geneFunctions.isEmpty()){
            throw new FunctionNotFoundException("Gene with ID " + geneId + " dosent have functions");
        }
        return geneFunctions.stream()
                .map(this::convertToGeneFunctionOutDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CreateGeneFunctionOutDTO addFunctionToGene(Long geneId, Long functionId, GeneFunctionInDTO geneFunctionInDTO) {
        CreateGeneFunctionOutDTO result = new CreateGeneFunctionOutDTO();

        // Validar que el gen existe
        if (!geneRepository.existsById(geneId)) {
            result.setSucess(false);
            result.setErrorMessage("Gene with ID " + geneId + " does not exist");
            return result;
        }

        // Validar que la función existe
        if (!functionService.existsById(functionId)) {
            result.setSucess(false);
            result.setErrorMessage("Function with ID " + functionId + " does not exist");
            return result;
        }

        // Validar que la asociación no existe ya
        if (geneFunctionRepository.existsByGeneIdAndFunctionId(geneId, functionId)) {
            result.setSucess(false);
            result.setErrorMessage("This gene is already associated with this function");
            return result;
        }

        // Crear la nueva asociación
        GeneFunction geneFunction = new GeneFunction();

        Gene gene = geneRepository.getReferenceById(geneId);
        geneFunction.setGene(gene);

        Function function = new Function();
        function.setId(functionId);
        geneFunction.setFunction(function);

        if (geneFunctionInDTO != null && geneFunctionInDTO.getEvidence() != null) {
            geneFunction.setEvidence(geneFunctionInDTO.getEvidence());
        }

        GeneFunction savedGeneFunction = geneFunctionRepository.save(geneFunction);

        result.setSucess(true);
        result.setGeneFunctionId(savedGeneFunction.getId());
        result.setErrorMessage("Function successfully associated with gene");

        return result;
    }
    /**
     * Elimina la asociación entre un gen y una función
     * @param geneId identificador del gen
     * @param functionId identificador de la función
     * @return true si se eliminó, false si no existe la asociación
     */
    @Override
    public boolean removeFunctionFromGene(Long geneId, Long functionId) {
        Optional<GeneFunction> geneFunctionOpt = geneFunctionRepository
                .findByGeneIdAndFunctionId(geneId, functionId);

        if (geneFunctionOpt.isPresent()) {
            geneFunctionRepository.delete(geneFunctionOpt.get());
            return true;
        }

        return false;
    }


    private GeneFunctionOutDTO convertToGeneFunctionOutDTO(GeneFunction geneFunction) {
        GeneFunctionOutDTO dto = new GeneFunctionOutDTO();
        dto.setId(geneFunction.getId());
        dto.setGeneId(geneFunction.getGene().getId());
        dto.setFunctionId(geneFunction.getFunction().getId());
        dto.setFunctionCode(geneFunction.getFunction().getCode());
        dto.setFunctionName(geneFunction.getFunction().getName());
        dto.setCategory(geneFunction.getFunction().getCategory());
        dto.setEvidence(geneFunction.getEvidence());
        return dto;
    }
    private GeneFunction geneFuncionDTOToEntity(GeneFunctionDTO dto, Long idGeneFunction) {
        if (dto == null) return null;
        GeneFunction geneFunction = new GeneFunction();
        Gene gene = new Gene();
        gene.setId(idGeneFunction);
        geneFunction.setGene(gene);
        Function function = new Function();
        function.setId(dto.getFunction());
        geneFunction.setFunction(function);
        geneFunction.setEvidence(dto.getEvidence());
        return geneFunction;
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