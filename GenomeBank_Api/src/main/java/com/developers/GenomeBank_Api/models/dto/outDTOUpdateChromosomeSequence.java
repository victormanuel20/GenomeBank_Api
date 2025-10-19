package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class outDTOUpdateChromosomeSequence extends ResutDTO {
    private Long id;
    private String name;
    private Integer oldLength;
    private Integer newLength;
    private String sequence;     // Secuencia nueva
}
