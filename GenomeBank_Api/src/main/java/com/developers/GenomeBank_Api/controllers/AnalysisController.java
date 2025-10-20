package com.developers.GenomeBank_Api.controllers;

import com.developers.GenomeBank_Api.models.dto.GetGenesInRangeOutDTO;
import com.developers.GenomeBank_Api.models.dto.GetSequenceStatsOutDTO;
import com.developers.GenomeBank_Api.services.IAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el módulo de análisis genético.
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    private final IAnalysisService analysisService;

    public AnalysisController(IAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    /**
     * Endpoint para obtener los genes ubicados en un rango cromosómico.
     */
    @GetMapping("/genes")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<GetGenesInRangeOutDTO>> getGenesInRange(
            @RequestParam Long chromosomeId,
            @RequestParam Integer start,
            @RequestParam Integer end) {

        List<GetGenesInRangeOutDTO> result = analysisService.getGenesByRange(chromosomeId, start, end);
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint para calcular estadísticas básicas de un cromosoma:
     * GC%, longitud y número de genes.
     */
    @GetMapping("/sequence/stats")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<GetSequenceStatsOutDTO> getSequenceStats(
            @RequestParam(required = false) Long chromosomeId) {

        GetSequenceStatsOutDTO result = analysisService.getSequenceStats(chromosomeId);
        return ResponseEntity.ok(result);
    }
}
