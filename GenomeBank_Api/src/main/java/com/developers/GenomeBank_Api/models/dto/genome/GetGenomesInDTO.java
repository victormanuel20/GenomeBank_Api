package com.developers.GenomeBank_Api.models.dto.genome;

import lombok.Data;

/**
 * Input DTO for listing genomes with an optional species filter.
 * In GET requests, this is built from query parameters (no JSON body).
 */
@Data
public class GetGenomesInDTO {
    private Long speciesId; // null => list all
}
