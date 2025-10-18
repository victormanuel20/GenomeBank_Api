package com.developers.GenomeBank_Api.models.dto.genome;

import com.developers.GenomeBank_Api.models.dto.ResultDTO;
import lombok.Data;

/**
 * DTO returned after attempting to delete a genome.
 * Extends ResultDTO (isSuccess, ErrorMessage) to include the target id.
 */
@Data
public class DeleteGenomeOutDTO extends ResultDTO {
    private Long id;   // genome id requested to delete (useful for logs/echo)
}
