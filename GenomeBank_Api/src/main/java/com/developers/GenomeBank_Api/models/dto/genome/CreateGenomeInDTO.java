package com.developers.GenomeBank_Api.models.dto.genome;

import lombok.Data;

/**
 * DTO used to receive genome creation data from the client.
 */
@Data
public class CreateGenomeInDTO {
    private String version;     // genome version or assembly name (e.g., GRCh38)
    private Long speciesId;     // reference to the species to which it belongs
}
