package com.developers.GenomeBank_Api.models.dto.geneDtos;

import lombok.Data;

@Data
public class GeneWithSequenceOutDTO extends GeneOutDTO {
    private String sequence;
}
