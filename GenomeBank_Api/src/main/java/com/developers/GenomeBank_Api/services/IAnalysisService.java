package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.analysisDto.GetGenesInRangeOutDTO;
import com.developers.GenomeBank_Api.models.dto.analysisDto.GetSequenceStatsOutDTO;

import java.util.List;

/**
 * Interfaz de servicio para el análisis genómico dentro del sistema GenomeBank.
 * Define las operaciones de negocio destinadas al procesamiento y análisis de datos
 * biológicos, como la comparación de secuencias, la detección de patrones genéticos
 * o la generación de resultados estadísticos sobre las entidades genómicas almacenadas.
 */
public interface IAnalysisService {
    public List<GetGenesInRangeOutDTO> getGenesByRange(Long chromosomeId,
                                                       Integer start, Integer end);

    public GetSequenceStatsOutDTO getSequenceStats(Long chromosomeId);
}
