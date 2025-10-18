package com.developers.GenomeBank_Api.controllers;

import com.developers.GenomeBank_Api.models.dto.genome.*;
import com.developers.GenomeBank_Api.services.IGenomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de genomas.
 * Proporciona los endpoints requeridos en el proyecto para los genomas.
 * La seguridad de los endpoints se gestiona mediante anotaciones @PreAuthorize para roles específicos.
 */
@RestController
@RequestMapping("/genomes")
public class GenomeController {

    private final IGenomeService genomeService;

    public GenomeController(IGenomeService genomeService) {
        this.genomeService = genomeService;
    }

    /**
     * Endpoint to create a new Genome.
     * Accessible only to users with ADMIN role.
     *
     * @param createGenomeInDTO input data (version and speciesId)
     * @return response containing success or error details
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateGenomeOutDTO> createGenome(@RequestBody CreateGenomeInDTO createGenomeInDTO) {

        CreateGenomeOutDTO response = this.genomeService.createGenome(createGenomeInDTO);

        if (!response.isSucess()) {

            return ResponseEntity.badRequest().body(response); // HTTP 400
        }

        return ResponseEntity.status(201).body(response);


    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteGenomeOutDTO> deleteGenome(@PathVariable Long id) {

        DeleteGenomeOutDTO response = this.genomeService.deleteGenome(id);

        if (!response.isSucess()) {

            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }


    @GetMapping("")
    public ResponseEntity<List<GenomeOutDTO>> getGenomes(
            @RequestParam(required = false) Long speciesId) {

        GetGenomesInDTO inDTO = new GetGenomesInDTO();
        inDTO.setSpeciesId(speciesId);

        List<GenomeOutDTO> response = genomeService.getGenomes(inDTO);

        return ResponseEntity.ok(response);
    }



}
