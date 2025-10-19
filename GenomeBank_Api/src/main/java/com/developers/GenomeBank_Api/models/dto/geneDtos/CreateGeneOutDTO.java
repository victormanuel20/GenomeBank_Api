package com.developers.GenomeBank_Api.models.dto.geneDtos;

import com.developers.GenomeBank_Api.models.dto.ResultDTO;

public class CreateGeneOutDTO extends ResultDTO {
    private Long geneId;

    public Long getGeneId() {
        return geneId;
    }

    public void setGeneId(Long geneId) {
        this.geneId = geneId;
    }
}