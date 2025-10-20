package com.developers.GenomeBank_Api.models.dto.genome;

import lombok.Data;

/**
 * Input DTO for getting a genome by ID
 */
@Data
public class GetGenomeByIdInDTO {
    private Long id;
}