package com.developers.GenomeBank_Api.controllers;

import com.developers.GenomeBank_Api.models.dto.*;
import com.developers.GenomeBank_Api.models.entities.Chromosome;
import com.developers.GenomeBank_Api.services.impl.ChromosomeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chromosomes")
public class ChromosomeController {

    private final ChromosomeService chromosomeService;

    // Constructor donde se inyecta la instancia de ChromosomeService
    public ChromosomeController(ChromosomeService chromosomeService) {
        this.chromosomeService = chromosomeService;
    }

    // Listar todos los cromosomas o filtrar por genoma
    @GetMapping("")
    public outDTOListChromosome getAllChromosomes(@RequestParam(required = false) Long genomeId) {
        return chromosomeService.getAllChromosomes(genomeId);
    }

    // Consultar un cromosoma específico
    @GetMapping("/{id}")
    public outDTOConsultSpecificChromosome getChromosomeById(@PathVariable Long id) {
        return chromosomeService.getChromosomeById(id);
    }

    // Crear un nuevo cromosoma (solo ADMIN)
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public outDTOCreateChromosome createChromosome(@RequestBody inDTOCreateChromosome chromosomeInDTO) {
        return chromosomeService.createChromosome(chromosomeInDTO);
    }

    // Actualizar un cromosoma (solo ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public outDTOUpdateChromosome updateChromosome(
            @PathVariable Long id,
            @RequestBody inDTOUpdateChromosome chromosomeInDTO) {
        return chromosomeService.updateChromosome(id, chromosomeInDTO);
    }


    // Eliminar un cromosoma (solo ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteChromosome(@PathVariable Long id) {
        chromosomeService.deleteChromosome(id);
    }

    // Consultar la secuencia completa de ADN de un cromosoma
    @GetMapping("/{id}/sequence")
    public String getChromosomeSequence(@PathVariable Long id) {
        return chromosomeService.getChromosomeSequence(id);
    }

    // Consultar una subsecuencia por rango
    @GetMapping("/{id}/sequence/range")
    public String getChromosomeSubSequence(@PathVariable Long id, @RequestParam int start, @RequestParam int end) {
        return chromosomeService.getChromosomeSubSequence(id, start, end);
    }

    // Registrar o actualizar la secuencia de un cromosoma (solo ADMIN)
    @PutMapping("/{id}/sequence")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateChromosomeSequence(@PathVariable Long id, @RequestBody String sequence) {
        chromosomeService.updateChromosomeSequence(id, sequence);
    }
}
