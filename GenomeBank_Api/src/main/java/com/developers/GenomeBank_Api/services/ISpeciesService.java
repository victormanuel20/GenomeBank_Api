package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.speciesDtos.CreateSpeciesInDTO;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.CreateSpeciesOutDTO;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.GetSpeciesByIdOutDTO;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.GetAllSpeciesOutDTO;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de especies.
 * Establece las operaciones necesarias para crear, consultar, actualizar y eliminar Species.
 */
public interface ISpeciesService {
    public List<GetAllSpeciesOutDTO> getAllSpecies();
    public GetSpeciesByIdOutDTO getSpeciesById(Long id);
    public CreateSpeciesOutDTO createSpecies(CreateSpeciesInDTO createSpeciesInDTO);
}
