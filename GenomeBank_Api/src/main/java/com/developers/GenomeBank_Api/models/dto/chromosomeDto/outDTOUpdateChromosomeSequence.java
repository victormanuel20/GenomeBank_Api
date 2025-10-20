package com.developers.GenomeBank_Api.models.dto.chromosomeDto;

import com.developers.GenomeBank_Api.models.dto.ResutDTO;
import lombok.Data;

@Data
public class outDTOUpdateChromosomeSequence extends ResutDTO {
    private Long id;
    private String name;
    private Integer oldLength;
    private Integer newLength;
    private String sequence;     // Secuencia nueva
}
