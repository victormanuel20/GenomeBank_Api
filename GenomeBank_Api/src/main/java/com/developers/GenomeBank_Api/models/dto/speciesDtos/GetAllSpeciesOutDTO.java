package com.developers.GenomeBank_Api.models.dto.speciesDtos;

import lombok.Data;

/**
 * Clase DTO para devolver el cuerpo
 * de consultas de especies
 */
@Data
public class GetAllSpeciesOutDTO {
    private String scientificName;
    private String commonName;
}
