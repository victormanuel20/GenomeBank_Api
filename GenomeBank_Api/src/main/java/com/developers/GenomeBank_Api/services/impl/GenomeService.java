package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.exceptions.GenomeNotFoundException;
import com.developers.GenomeBank_Api.exceptions.InvalidSpeciesFilterException;
import com.developers.GenomeBank_Api.models.dto.genome.*;
import com.developers.GenomeBank_Api.models.entities.Genome;
import com.developers.GenomeBank_Api.models.entities.Species;
import com.developers.GenomeBank_Api.repositories.GenomeRepository;
import com.developers.GenomeBank_Api.repositories.SpeciesRepository;
import com.developers.GenomeBank_Api.services.IGenomeService;
import com.developers.GenomeBank_Api.services.ISpeciesService;
import org.springframework.stereotype.Service;

import java.util.List;

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



    @Override
    public List<GenomeOutDTO> getGenomes(GetGenomesInDTO inDTO) {

        List<Genome> genomes;

        // Si viene speciesId, filtramos por especie
        if (inDTO.getSpeciesId() != null) {

            // Validar que la especie existe antes de buscar
            if (!this.speciesService.existsSpecies(inDTO.getSpeciesId())) {
                // Lanzar excepción específica para filtro inválido
                throw new InvalidSpeciesFilterException(inDTO.getSpeciesId());
            }

            // Buscar genomas de esa especie específica
            genomes = this.genomeRepository.findBySpeciesId(inDTO.getSpeciesId());

        } else {
            // Si NO viene speciesId, listar TODOS los genomas
            genomes = this.genomeRepository.findAll();
        }

        // Convertir las entidades Genome a DTOs GenomeOutDTO
        return genomes.stream()
                .map(this::convertToOutDTO)
                .toList();
    }

    /**
     * Método auxiliar para convertir una entidad Genome a GenomeOutDTO
     * @param genome entidad Genome
     * @return GenomeOutDTO con la información del genoma y su especie
     */
    private GenomeOutDTO convertToOutDTO(Genome genome) {
        GenomeOutDTO dto = new GenomeOutDTO();

        // Información del genoma
        dto.setId(genome.getId());
        dto.setVersion(genome.getVersion());

        // Información de la especie asociada (para dar contexto)
        if (genome.getSpecies() != null) {
            dto.setSpeciesId(genome.getSpecies().getId());
            dto.setSpeciesScientificName(genome.getSpecies().getScientificName());
            dto.setSpeciesCommonName(genome.getSpecies().getCommonName());
        }

        return dto;
    }

    /*
    @Override
    public List<GenomeOutDTO> getGenomes(GetGenomesInDTO inDTO) {
        List<Genome> genomes;

        // ¿Viene speciesId en el query param?
        if (inDTO.getSpeciesId() != null) {
            Long speciesId = inDTO.getSpeciesId();

            // 1) Si la especie NO existe → 404 con tu GenomeNotFoundException
            if (!speciesRepository.existsById(speciesId)) {
                throw new GenomeNotFoundException("Species not found with id: " + speciesId);
            }

            // 2) Traer genomas por especie
            genomes = genomeRepository.findBySpeciesId(speciesId);

            // 3) Si no hay genomas para esa especie → 404
            if (genomes.isEmpty()) {
                throw new GenomeNotFoundException("No genomes found for species id: " + speciesId);
            }

        } else {
            // Sin filtro → traer todos
            genomes = genomeRepository.findAll();

            // Si la base está vacía → 404
            if (genomes.isEmpty()) {
                throw new GenomeNotFoundException("No genomes found in the database.");
            }
        }

        // Mapear a DTOs de salida (sin exponer Entities)
        return genomes.stream()
                .map(this::toOutDTO)
                .toList();
    }

    private GenomeOutDTO toOutDTO(Genome genome) {
        GenomeOutDTO dto = new GenomeOutDTO();
        dto.setId(genome.getId());
        dto.setVersion(genome.getVersion());
        dto.setSpeciesId(genome.getSpecies().getId());
        dto.setSpeciesScientificName(genome.getSpecies().getScientificName());
        dto.setSpeciesCommonName(genome.getSpecies().getCommonName());
        return dto;
    }

     */


}
