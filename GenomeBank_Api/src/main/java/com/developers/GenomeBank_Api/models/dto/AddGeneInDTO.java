package com.developers.GenomeBank_Api.models.dto;


import lombok.Data;

@Data
public class AddGeneInDTO {

    private String symbol;
    private Integer startPosition;
    private Integer endPosition;
    private String strand;
    private String sequence;

    private Long chromosome;

}
