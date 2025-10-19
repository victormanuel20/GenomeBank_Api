package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class GeneOutDTO {
    private Long id;
    private String symbol;
    private int startPosition;
    private int endPosition;
    private String strand;
    private Long chromosomeId;
    private String chromosomeName;
}