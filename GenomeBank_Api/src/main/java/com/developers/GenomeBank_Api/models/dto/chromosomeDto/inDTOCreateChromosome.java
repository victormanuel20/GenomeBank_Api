package com.developers.GenomeBank_Api.models.dto.chromosomeDto;

import lombok.Data;

@Data
public class inDTOCreateChromosome {
    private String name;    // Nombre del cromosoma
    private int length;     // Longitud del cromosoma
    private String sequence; // Secuencia de ADN
    private Long genomeId;
}

