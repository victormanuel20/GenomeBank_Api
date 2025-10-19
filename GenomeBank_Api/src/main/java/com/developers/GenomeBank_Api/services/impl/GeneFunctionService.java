package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.models.entities.GeneFunction;
import com.developers.GenomeBank_Api.repositories.GeneFunctionRepository;
import com.developers.GenomeBank_Api.services.IGeneFunctionService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Implementación del servicio de GenesFunciones.
 * Proporciona la lógica de negocio para la gestión de GenesFunciones, incluyendo operaciones CRUD.
 * Utiliza GenFuncionRepository para el acceso a datos.
 */
@Service
public class GeneFunctionService implements IGeneFunctionService {

    private final GeneFunctionRepository geneFunctionRepository;

    public GeneFunctionService(GeneFunctionRepository geneFunctionRepository) {
        this.geneFunctionRepository = geneFunctionRepository;
    }

    @Override
    public void createGeneFunctionBatch(Set<GeneFunction> functionGene) {
        this.geneFunctionRepository.saveAll(functionGene);
    }
}
