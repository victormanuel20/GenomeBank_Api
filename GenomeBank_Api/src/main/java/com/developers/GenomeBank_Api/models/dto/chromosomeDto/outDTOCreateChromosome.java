package com.developers.GenomeBank_Api.models.dto.chromosomeDto;

import com.developers.GenomeBank_Api.models.dto.ResutDTO;
import lombok.Data;

@Data
public class outDTOCreateChromosome extends ResutDTO {
    private Long id;       // ID del cromosoma
    private String name;   // Nombre del cromosoma
    private int length;    // Longitud del cromosoma
    private String sequence; // Secuencia de ADN
    private GenomeDTO genome; // Información del genoma

    @Data
    public static class GenomeDTO {
        private Long id;
        private String version;
    }
}

