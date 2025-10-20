package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.exceptions.genomeException.GenomeNotFoundException;
import com.developers.GenomeBank_Api.exceptions.genomeException.InvalidSpeciesFilterException;
import com.developers.GenomeBank_Api.exceptions.genomeException.InvalidSpeciesForGenomeException;
import com.developers.GenomeBank_Api.exceptions.genomeException.MissingFieldsException;
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

    @Override
    public GetGenomeByIdOutDTO getGenomeById(GetGenomeByIdInDTO inDTO) {

        GetGenomeByIdOutDTO outDTO = new GetGenomeByIdOutDTO();

        // Buscar el genoma por ID (lanza GenomeNotFoundException si no existe)
        Genome genome = this.genomeRepository.findById(inDTO.getId())
                .orElseThrow(() -> new GenomeNotFoundException(inDTO.getId()));

        // Copiar información del genoma al DTO
        outDTO.setId(genome.getId());
        outDTO.setVersion(genome.getVersion());

        // Copiar información de la especie asociada
        if (genome.getSpecies() != null) {
            outDTO.setSpeciesId(genome.getSpecies().getId());
            outDTO.setSpeciesScientificName(genome.getSpecies().getScientificName());
            outDTO.setSpeciesCommonName(genome.getSpecies().getCommonName());
        }

        // Respuesta exitosa
        outDTO.setSucess(true);

        return outDTO;
    }

    @Override
    public UpdateGenomeOutDTO updateGenome(Long id, UpdateGenomeInDTO inDTO) {

        UpdateGenomeOutDTO outDTO = new UpdateGenomeOutDTO();

        // 1. VALIDAR que version no sea null ni vacío
        if (inDTO.getVersion() == null || inDTO.getVersion().trim().isEmpty()) {
            throw new MissingFieldsException("Version is required and cannot be empty");
        }

        // 2. VALIDAR que speciesId no sea null
        if (inDTO.getSpeciesId() == null) {
            throw new MissingFieldsException("Species ID is required");
        }

        // 3. Buscar el genoma a actualizar
        Genome genome = this.genomeRepository.findById(id)
                .orElseThrow(() -> new GenomeNotFoundException(id));

        // 4. Validar que la especie existe
        if (!this.speciesService.existsSpecies(inDTO.getSpeciesId())) {
            throw new InvalidSpeciesForGenomeException(inDTO.getSpeciesId());
        }

        // 5. Obtener la especie
        Species species = this.speciesRepository.getReferenceById(inDTO.getSpeciesId());

        // 6. Actualizar los campos del genoma
        genome.setVersion(inDTO.getVersion().trim());
        genome.setSpecies(species);

        // 7. Guardar cambios
        Genome updatedGenome = this.genomeRepository.save(genome);

        // 8. Construir respuesta exitosa
        outDTO.setSucess(true);
        outDTO.setId(updatedGenome.getId());
        outDTO.setVersion(updatedGenome.getVersion());
        outDTO.setSpeciesId(updatedGenome.getSpecies().getId());
        outDTO.setSpeciesScientificName(updatedGenome.getSpecies().getScientificName());
        outDTO.setSpeciesCommonName(updatedGenome.getSpecies().getCommonName());

        return outDTO;
    }


}
