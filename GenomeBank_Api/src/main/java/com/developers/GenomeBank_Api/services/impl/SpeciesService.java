package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.models.dto.speciesDtos.CreateSpeciesInDTO;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.CreateSpeciesOutDTO;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.GetSpeciesByIdOutDTO;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.GetAllSpeciesOutDTO;
import com.developers.GenomeBank_Api.models.entities.Species;
import com.developers.GenomeBank_Api.repositories.SpeciesRepository;
import com.developers.GenomeBank_Api.services.ISpeciesService;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("Species found with id: " + id));

        // Se crea el DTO con el id de la especie encontrada
        GetSpeciesByIdOutDTO dto = new GetSpeciesByIdOutDTO();
        dto.setScientificName(species.getScientificName());
        dto.setCommonName(species.getCommonName());

        if (species.getId() != null) {
            dto.setExitoso(true);
        }
        else {
            dto.setExitoso(false);
            dto.setMensajeError("Error al encontar especie por id: " + id);
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

        Species species = new Species();
        species.setScientificName(createSpeciesInDTO.getScientificName());
        species.setCommonName(createSpeciesInDTO.getCommonName());
        this.speciesRepository.save(species);

        if (species.getId() != null) {
            dto.setExitoso(true);
        }
        else {
            dto.setExitoso(false);
            dto.setMensajeError("Error al crear especie, no se encontró ID");
        }

        return dto;
    }

}
