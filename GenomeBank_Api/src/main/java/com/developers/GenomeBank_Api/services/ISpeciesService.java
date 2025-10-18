package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.speciesDtos.*;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de especies.
 * Establece las operaciones necesarias para crear, consultar, actualizar y eliminar Species.
 */
public interface ISpeciesService {
    public List<GetAllSpeciesOutDTO> getAllSpecies();
    public GetSpeciesByIdOutDTO getSpeciesById(Long id);
    public CreateSpeciesOutDTO createSpecies(CreateSpeciesInDTO createSpeciesInDTO);
    public UpdateSpeciesOutDTO updateSpecies(Long id, UpdateSpeciesInDTO
            updateSpeciesInDTO);

    public DeleteSpeciesOutDTO deleteSpecies(Long id);

}
