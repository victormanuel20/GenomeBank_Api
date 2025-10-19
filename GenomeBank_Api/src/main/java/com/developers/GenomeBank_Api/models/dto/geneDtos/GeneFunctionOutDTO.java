package com.developers.GenomeBank_Api.models.dto.geneDtos;

import lombok.Data;

@Data
public class GeneFunctionOutDTO {
    private Long id;
    private Long geneId;
    private Long functionId;
    private String functionCode;
    private String functionName;
    private String category;
    private String evidence;
}