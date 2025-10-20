package com.developers.GenomeBank_Api.models.dto.geneDtos;

import lombok.Data;

@Data
public class UpdateGeneInDTO {
    private String symbol;
    private Integer startPosition;
    private Integer endPosition;
    private String strand;
    private String sequence;
}