package com.developers.GenomeBank_Api.models.dto.genome;
import lombok.Data;

/**
 * Input DTO for updating a genome
 * All fields are required
 */

@Data
public class UpdateGenomeInDTO {
    private String version;
    private Long speciesId;
}
