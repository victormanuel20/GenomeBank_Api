package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.models.entities.Gene;
import com.developers.GenomeBank_Api.repositories.GeneRepository;
import com.developers.GenomeBank_Api.services.IGeneService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementación del servicio de genes.
 * Proporciona la lógica de negocio para la gestión de genes, incluyendo operaciones CRUD.
 * Utiliza GenRepository para el acceso a datos.
 */
@Service
public class GeneService implements IGeneService {

    private GeneRepository geneRepository;

    /**
     * Constructor que inyecta el repositorio de autores.
     * @param geneRepository repositorio de autores
     */
    public GeneService(GeneRepository geneRepository) {
        this.geneRepository = geneRepository;
    }

    @Override
    public Optional<Gene> getGene(Long id) {return this.geneRepository.findById(id);

    }



}
