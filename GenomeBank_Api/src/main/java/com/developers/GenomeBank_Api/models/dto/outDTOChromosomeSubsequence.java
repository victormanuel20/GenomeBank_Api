package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class outDTOChromosomeSubsequence extends ResutDTO {
    private Long id;          // ID del cromosoma
    private Integer start;    // Inicio tipo vector[0,1,2,3,..]
    private Integer end;      // Final, este funciona como un vector
    private Integer totalLength; // Longitud del cromosoma
    private String subsequence;  // Subsecuencia buscada
}
