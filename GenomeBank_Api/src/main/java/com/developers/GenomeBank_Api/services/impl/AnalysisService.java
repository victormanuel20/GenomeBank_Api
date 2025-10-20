package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.exceptions.ChromosomeNotFoundException;
import com.developers.GenomeBank_Api.models.dto.GetGenesInRangeOutDTO;
import com.developers.GenomeBank_Api.models.dto.GetSequenceStatsOutDTO;
import com.developers.GenomeBank_Api.models.entities.Chromosome;
import com.developers.GenomeBank_Api.models.entities.Gene;
import com.developers.GenomeBank_Api.repositories.ChromosomeRepository;
import com.developers.GenomeBank_Api.repositories.GeneRepository;
import com.developers.GenomeBank_Api.services.IAnalysisService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de análisis.
 * Proporciona la lógica de negocio para la gestión de análisis, incluyendo operaciones CRUD.
 * Utiliza AnalysisRepository para el acceso a datos.
 */
@Service
public class AnalysisService implements IAnalysisService {

    private final GeneRepository geneRepository;
    private final ChromosomeRepository chromosomeRepository;

    public AnalysisService(GeneRepository geneRepository,
                           ChromosomeRepository chromosomeRepository) {
        this.chromosomeRepository = chromosomeRepository;
        this.geneRepository = geneRepository;
    }

    @Override
    public List<GetGenesInRangeOutDTO> getGenesByRange(Long chromosomeId, Integer start, Integer end) {
        List<Gene> genes = geneRepository.findByChromosomeIdAndPositionRange(chromosomeId, start, end);

        return genes.stream()
                .map(g -> {
                    GetGenesInRangeOutDTO dto = new GetGenesInRangeOutDTO();
                    dto.setSymbol(g.getSymbol());
                    dto.setSequence(g.getSequence());
                    dto.setStartPosition(g.getStartPosition());
                    dto.setEndPosition(g.getEndPosition());
                    dto.setChromosomeId(g.getChromosome().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public GetSequenceStatsOutDTO getSequenceStats(Long chromosomeId) {
        Chromosome chr = chromosomeRepository.findById(chromosomeId)
                .orElseThrow(() -> new ChromosomeNotFoundException("Cromosoma no encontrado con ID: " + chromosomeId));

        String seq = chr.getSequence().toUpperCase();
        int length = seq.length();

        long gcCount = seq.chars()
                .filter(base -> base == 'G' || base == 'C')
                .count();

        double gcPercentage = (double) gcCount / length * 100;

        int geneCount = geneRepository.findByChromosomeId(chromosomeId).size();

        GetSequenceStatsOutDTO dto = new GetSequenceStatsOutDTO();
        dto.setChromosomeName(chr.getName());
        dto.setLength(length);
        dto.setGcPercentage(gcPercentage);
        dto.setGeneCount(geneCount);

        return dto;
    }
}
