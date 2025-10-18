package com.developers.GenomeBank_Api.models.dto.speciesDtos;

import lombok.Data;

/**
 * Clase inDTO para actualizar el cuerpo de una especie
 */
@Data
public class UpdateSpeciesInDTO {
    private String scientificName;
    private String commonName;
}
