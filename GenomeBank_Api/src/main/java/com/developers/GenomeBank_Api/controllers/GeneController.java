package com.developers.GenomeBank_Api.controllers;

import com.developers.GenomeBank_Api.models.dto.*;

import com.developers.GenomeBank_Api.services.IGeneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
     * Crea un nuevo gen.
     * @param createGeneInDTO data del gen
     * @return ResponseEntity con el resultado de la creacion
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


    /**
     * Actualiza el gen
     * @param id identidficador del gen
     * @param updateGeneInDTO datos del gen a actualizar
     * @return ResponseEntity con el gen actualizado o 404 si no lo encuentra
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GeneOutDTO> updateGene(
            @PathVariable Long id,
            @RequestBody UpdateGeneInDTO updateGeneInDTO) {
        try {
            return geneService.updateGene(id, updateGeneInDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina un gen
     * @param id identidficador del gen
     * @return ResponseEntity con 204 si es eliminado o 404 si no lo encuentra
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGene(@PathVariable Long id) {
        if (geneService.deleteGene(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Obtiene la secuencia del gen
     * @param id identificador del gen
     * @return ResponseEntity con la secuencia o 404 si no encuentra
     */
    @GetMapping("/{id}/sequence")
    public ResponseEntity<Map<String, String>> getGeneSequence(@PathVariable Long id) {
        return geneService.getGeneSequence(id)
                .map(sequence -> ResponseEntity.ok(Map.of("sequence", sequence)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza la secuencia del gen
     * @param id identificador del gen
     * @param updateGeneSequenceInDTO nueva secuencia a actualizar
     * @return ResponseEntity con el gen actualizado o 404 si no se encuentra
     */
    @PutMapping("/{id}/sequence")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GeneOutDTO> updateGeneSequence(
            @PathVariable Long id,
            @RequestBody UpdateGeneSequenceInDTO updateGeneSequenceInDTO) {
        try {
            return geneService.updateGeneSequence(id, updateGeneSequenceInDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
