package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.exceptions.SpeciesNotCreatedException;
import com.developers.GenomeBank_Api.exceptions.SpeciesNotFoundException;
import com.developers.GenomeBank_Api.exceptions.SpeciesNotUpdatedException;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.*;
import com.developers.GenomeBank_Api.models.entities.Species;
import com.developers.GenomeBank_Api.repositories.SpeciesRepository;
import com.developers.GenomeBank_Api.services.ISpeciesService;
import org.springframework.stereotype.Service;
import com.developers.GenomeBank_Api.exceptions.DuplicateSpeciesException;

import java.util.List;

/**
 * Implementación del servicio de especies.
 * Proporciona la lógica de negocio para la gestión de especies, incluyendo operaciones CRUD.
 * Utiliza EspecieRepository para el acceso a datos.
 */
@Service
public class SpeciesService implements ISpeciesService {

    /**
     * Se llama el repositorio de especies
     * Trae los métodos JPA
     */
    private final SpeciesRepository speciesRepository;

    /**
     * Se inyecta el repositorio de especies
     * para hacer uso de él
     */
    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    /**
     * Método que maneja la lógica de negocio
     * para devolver la lista de todas las especies
     * @return List<Species>
     */
    @Override
    public List<GetAllSpeciesOutDTO> getAllSpecies() {
        // findAll() devuelve lista de Species, no DTOs
        List<Species> entity = speciesRepository.findAll();

        // Transformamos a DTOs usando streams
        return entity.stream()
                .map(species -> {
                    GetAllSpeciesOutDTO dto = new GetAllSpeciesOutDTO();
                    dto.setScientificName(species.getScientificName());
                    dto.setCommonName(species.getCommonName());

                    return dto;
                })
                .toList();
    }

    /**
     * Método que maneja la lógica de negocio
     * para devolver una especie por ID
     * @return species
     */
    @Override
    public GetSpeciesByIdOutDTO getSpeciesById(Long id) {
        // findById() busca el id de la especie, no el DTO
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new SpeciesNotFoundException("Species not found with id: " + id));

        // Se crea el DTO con el id de la especie encontrada
        GetSpeciesByIdOutDTO dto = new GetSpeciesByIdOutDTO();
        dto.setScientificName(species.getScientificName());
        dto.setCommonName(species.getCommonName());

        if (species.getId() != null) {
            dto.setSucess(true);
        }
        else {
            dto.setSucess(false);
            dto.setErrorMessage("Error al encontar especie por id: " + id);
        }

        return dto;
    }

    /**
     * Método para crear una especie implementando DTOs
     * @param createSpeciesInDTO
     * @return CreateSpeciesOutDTO
     */
    @Override
    public CreateSpeciesOutDTO createSpecies(CreateSpeciesInDTO createSpeciesInDTO) {
        CreateSpeciesOutDTO dto = new CreateSpeciesOutDTO();

        String sciName = createSpeciesInDTO.getScientificName();
        String comName = createSpeciesInDTO.getCommonName();

        if (sciName == null || sciName.isBlank() || comName == null || comName.isBlank()) {
            throw new SpeciesNotCreatedException("Debe llenar los campos de nombre científico y común.");
        }

        if (speciesRepository.findByScientificName(sciName).isPresent()) {
            throw new DuplicateSpeciesException("Ya existe una especie con ese nombre científico.");
        }
        if (speciesRepository.findByCommonName(comName).isPresent()) {
            throw new DuplicateSpeciesException("Ya existe una especie con ese nombre común.");
        }

        Species species = new Species();
        species.setScientificName(sciName);
        species.setCommonName(comName);
        speciesRepository.save(species);

        dto.setSucess(true);
        return dto;
    }

    /**
     * Método que actualiza la información total
     * de una especie
     * @param id
     * @param updateSpeciesInDTO
     * @return UpdateSpeciesOutDTO
     */
    @Override
    public UpdateSpeciesOutDTO updateSpecies(Long id, UpdateSpeciesInDTO updateSpeciesInDTO) {
        return speciesRepository.findById(id)
                .map(speciesFound -> {
                    UpdateSpeciesOutDTO dto = new UpdateSpeciesOutDTO();

                    if (updateSpeciesInDTO.getScientificName() == null || updateSpeciesInDTO.getCommonName()
                            == null) {

                        dto.setSucess(false);
                        dto.setErrorMessage("Error al actualizar especie");
                        throw new SpeciesNotUpdatedException("Debe ingresar tanto el nombre científico como " +
                                "el nombre común.");
                    }

                    speciesFound.setScientificName(updateSpeciesInDTO.getScientificName());
                    speciesFound.setCommonName(updateSpeciesInDTO.getCommonName());
                    speciesRepository.save(speciesFound);

                    dto.setSucess(true);
                    dto.setErrorMessage(null);

                    return dto;
                }).orElseThrow(() -> new SpeciesNotFoundException("Species not found with id: " + id));
    }

    @Override
    public DeleteSpeciesOutDTO deleteSpecies(Long id) {
        DeleteSpeciesOutDTO dto = new DeleteSpeciesOutDTO();

        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new SpeciesNotFoundException("Species not found with id: " + id));

        this.speciesRepository.delete(species);
        dto.setSucess(true);

        return dto;
    }

}
