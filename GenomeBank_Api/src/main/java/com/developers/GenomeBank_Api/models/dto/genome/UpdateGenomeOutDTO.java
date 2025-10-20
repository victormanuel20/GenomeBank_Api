package com.developers.GenomeBank_Api.models.dto.genome;

import com.developers.GenomeBank_Api.models.dto.ResultDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Output DTO for updating a genome
 */
@Data
public class UpdateGenomeOutDTO extends ResultDTO {

    private Long id;
    private String version;
    private Long speciesId;
    private String speciesScientificName;
    private String speciesCommonName;

}
