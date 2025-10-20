package com.developers.GenomeBank_Api.models.dto.chromosomeDto;

import com.developers.GenomeBank_Api.models.dto.ResutDTO;
import lombok.Data;

@Data
public class outDTOConsultSpecificChromosome extends ResutDTO {
    private Long id;
    private String name;
    private int length;
    private String sequence;
    private GenomeDTO genome;

    @Data
    public static class GenomeDTO {
        private Long id;
        private String version;
        private SpeciesDTO species;

        @Data
        public static class SpeciesDTO {
            private Long id;
            private String scientificName;
            private String commonName;
        }
    }
}
