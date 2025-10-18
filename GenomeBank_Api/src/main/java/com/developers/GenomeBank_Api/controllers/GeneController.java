package com.developers.GenomeBank_Api.controllers;

import com.developers.GenomeBank_Api.models.dto.CreateGeneInDTO;
import com.developers.GenomeBank_Api.models.dto.CreateGeneOutDTO;
import com.developers.GenomeBank_Api.models.dto.GeneOutDTO;
import com.developers.GenomeBank_Api.models.dto.GeneWithSequenceOutDTO;

import com.developers.GenomeBank_Api.services.IGeneService;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<GeneOutDTO>> getAllGenes(
            @RequestParam(required = false) Long chromosomeId,
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) Integer start,
            @RequestParam(required = false) Integer end) {
        List<GeneOutDTO> genes = geneService.getAllGenes(chromosomeId, symbol, start, end);
        return ResponseEntity.ok(genes);
    }

    /**
     * Obtiene un gen especifico por ekl ID.
     * @param id identificador del gen
     * @return El responseEntity del gen o si no 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<GeneWithSequenceOutDTO> getGeneById(@PathVariable Long id) {
        return geneService.getGeneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * Creates a new gene.
     * @param createGeneInDTO gene data
     * @return ResponseEntity with the creation result
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateGeneOutDTO> createGene(@RequestBody CreateGeneInDTO createGeneInDTO) {
        CreateGeneOutDTO result = geneService.createGene(createGeneInDTO);
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
