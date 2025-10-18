package com.developers.GenomeBank_Api.models.dto.genome;

import com.developers.GenomeBank_Api.models.dto.ResultDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO returned after successfully creating a genome.
 * Extends ResultDTO to include operation status.
 */
@Data
public class CreateGenomeOutDTO extends ResultDTO {
    private Long id;            // ID of the created genome
    private String version;     // version name of the genome
    private Long speciesId;     // ID of the associated species
}