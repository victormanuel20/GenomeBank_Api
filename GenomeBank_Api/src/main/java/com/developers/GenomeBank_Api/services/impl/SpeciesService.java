package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.repositories.SpeciesRepository;
import com.developers.GenomeBank_Api.services.ISpeciesService;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de especies.
 * Proporciona la lógica de negocio para la gestión de especies, incluyendo operaciones CRUD.
 * Utiliza EspecieRepository para el acceso a datos.
 */
@Service
public class SpeciesService implements ISpeciesService {

    private final SpeciesRepository speciesRepository;

    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public boolean existsSpecies(Long id) {
        return speciesRepository.existsById(id);
    }

}
