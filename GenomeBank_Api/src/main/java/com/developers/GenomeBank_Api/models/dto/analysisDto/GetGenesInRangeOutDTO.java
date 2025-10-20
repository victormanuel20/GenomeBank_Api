package com.developers.GenomeBank_Api.models.dto.analysisDto;

import lombok.Data;

/**
 * DTO de salida que representa un gen dentro del análisis de rango cromosómico.
 */
@Data
public class GetGenesInRangeOutDTO {
    private String symbol;
    private String sequence;
    private Integer startPosition;
    private Integer endPosition;
    private Long chromosomeId;
}
