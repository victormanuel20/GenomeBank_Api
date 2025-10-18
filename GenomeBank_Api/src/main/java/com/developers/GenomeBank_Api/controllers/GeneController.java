package com.developers.GenomeBank_Api.controllers;

import com.developers.GenomeBank_Api.models.entities.Gene;
import com.developers.GenomeBank_Api.services.IGeneService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de genes.
 * Proporciona los endpoints requeridos en el proyecto para los genes.
 * La seguridad de los endpoints se gestiona mediante anotaciones @PreAuthorize para roles específicos.
 */
@RestController
@RequestMapping("/genes")
public class GeneController {
    private final IGeneService geneService;

    public GeneController(IGeneService geneService) {this.geneService = geneService;}

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/genome")
    public ResponseEntity<List<Gene>> listGenes(){
        List<Gene> genes = geneService.getGenes();
        return ResponseEntity.ok(genes);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/genomes/{id}")
    public ResponseEntity<Gene> getAutorById(@PathVariable Long id) {
        return geneService.getGene(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede crear
    @PostMapping("/genomes")
    public ResponseEntity<Gene> createAutor(@RequestBody Gene gene) {
        return ResponseEntity.ok().body(geneService.addGene(gene));
    }
}
