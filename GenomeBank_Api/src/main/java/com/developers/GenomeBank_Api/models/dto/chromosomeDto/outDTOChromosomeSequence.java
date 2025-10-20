package com.developers.GenomeBank_Api.models.dto.chromosomeDto;

import com.developers.GenomeBank_Api.models.dto.ResutDTO;
import lombok.Data;

@Data
public class outDTOChromosomeSequence extends ResutDTO {
    private Long id;         // ID del cromosoma
    private String sequence; // Secuencia completa de ADN
}