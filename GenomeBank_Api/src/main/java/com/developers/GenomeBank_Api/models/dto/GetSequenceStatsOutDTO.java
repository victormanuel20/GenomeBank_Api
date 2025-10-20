package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class GetSequenceStatsOutDTO {
    private String chromosomeName;
    private int length;
    private double gcPercentage;
    private int geneCount;
}
