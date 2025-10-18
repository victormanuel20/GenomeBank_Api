package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

@Data
public class outDTOUpdateChromosome extends ResultDTO {
    private Long id;
    private String name;
    private int length;
    private String sequence;
    private GenomeDTO genome; // resumido (id + version)

    @Data
    public static class GenomeDTO {
        private Long id;
        private String version;
    }
}
