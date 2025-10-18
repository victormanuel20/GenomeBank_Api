package com.developers.GenomeBank_Api.models.dto.speciesDtos;

import com.developers.GenomeBank_Api.models.dto.ResultDTO;
import lombok.Data;

/**
 * Clase DTO para devolver el cuerpo
 * de consultas de especies por ID
 */
@Data
public class GetSpeciesByIdOutDTO extends ResultDTO {
    private String scientificName;
    private String commonName;
}
