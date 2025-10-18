package com.developers.GenomeBank_Api.models.dto.genome;

import lombok.Data;

/**
 * Output DTO representing a Genome in list/search responses.
 */
@Data
public class GenomeOutDTO {
    private Long id;
    private String version;

    // Minimal species info for context
    private Long speciesId;
    private String speciesScientificName;
    private String speciesCommonName;
}
