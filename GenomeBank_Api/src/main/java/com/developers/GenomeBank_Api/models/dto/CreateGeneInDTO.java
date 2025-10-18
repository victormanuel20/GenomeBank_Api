package com.developers.GenomeBank_Api.models.dto;


import lombok.Data;

@Data
public class CreateGeneInDTO {

    private String symbol;
    private Integer startPosition;
    private Integer endPosition;
    private String strand;
    private String sequence;

    private Long chromosome;

}
