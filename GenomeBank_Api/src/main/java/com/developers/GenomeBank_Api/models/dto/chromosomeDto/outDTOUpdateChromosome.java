package com.developers.GenomeBank_Api.models.dto.chromosomeDto;

import com.developers.GenomeBank_Api.models.dto.ResultDTO;
import lombok.Data;

@Data
public class outDTOUpdateChromosome extends ResultDTO {
    private Long id;
    private String name;
    private int length;
    private String sequence;
    private GenomeDTO genome;

    @Data
    public static class GenomeDTO {
        private Long id;
        private String version;
    }
}
