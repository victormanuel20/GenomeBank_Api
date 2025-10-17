package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.entities.Gene;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la administración de genes.
 * Define las operaciones de negocio que permiten gestionar los objetos Gene.
 */
public interface IGeneService {

    public final IGeneService geneSer

    public List<Gene> getGenes();
    public Optional<Gene> getGene(Long id);
    public Gene addGene(Gene gene);


}
