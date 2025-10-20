package com.developers.GenomeBank_Api.models.dto.genome;

import com.developers.GenomeBank_Api.models.dto.ResultDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Output DTO for getting a genome by ID
 */
@Data
public class GetGenomeByIdOutDTO extends ResultDTO {

    private Long id;
    private String version;

    // Species info for context
    private Long speciesId;
    private String speciesScientificName;
    private String speciesCommonName;

}
