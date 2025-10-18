package com.developers.GenomeBank_Api.models.dto.speciesDtos;

import lombok.Data;

/**
 * Clase DTO para crear el cuerpo de una especie
 */
@Data
public class CreateSpeciesInDTO {
    private String scientificName;
    private String commonName;
}
