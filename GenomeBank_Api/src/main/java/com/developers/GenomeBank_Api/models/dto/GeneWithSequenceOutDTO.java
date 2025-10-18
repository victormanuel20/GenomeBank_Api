package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class GeneWithSequenceOutDTO extends GeneOutDTO {
    private String sequence;
}
