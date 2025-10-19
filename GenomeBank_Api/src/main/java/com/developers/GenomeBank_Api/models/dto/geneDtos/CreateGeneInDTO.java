package com.developers.GenomeBank_Api.models.dto.geneDtos;


import lombok.Data;

import java.util.Set;

@Data
public class CreateGeneInDTO {

    private String symbol;
    private Integer startPosition;
    private Integer endPosition;
    private String strand;
    private String sequence;

    private Long chromosome;

    private Set<GeneFunctionDTO> functions;
}
