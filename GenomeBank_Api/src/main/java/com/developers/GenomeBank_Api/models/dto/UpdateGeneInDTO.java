package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class UpdateGeneInDTO {
    private String symbol;
    private Long startPosition;
    private Long endPosition;
    private String strand;
    private String sequence;
}