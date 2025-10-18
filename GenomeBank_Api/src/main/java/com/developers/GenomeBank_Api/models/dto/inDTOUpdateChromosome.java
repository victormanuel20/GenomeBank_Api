package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class inDTOUpdateChromosome {
    private String name;
    private int length;
    private String sequence;
}

