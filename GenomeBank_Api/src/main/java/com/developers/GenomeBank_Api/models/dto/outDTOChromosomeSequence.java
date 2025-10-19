package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class outDTOChromosomeSequence extends ResutDTO {
    private Long id;         // ID del cromosoma
    private String sequence; // Secuencia completa de ADN
}