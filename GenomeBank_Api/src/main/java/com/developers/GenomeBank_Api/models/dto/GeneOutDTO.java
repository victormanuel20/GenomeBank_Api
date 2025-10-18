package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class GeneOutDTO {
    private Integer id;
    private String symbol;
    private int startPosition;
    private int endPosition;
    private String strand;
    private Integer chromosomeId;
    private String chromosomeName;
}