package com.developers.GenomeBank_Api.models.dto.geneDtos;

import lombok.Data;


/**
 * DTO para recibir datos al crear/actualizar una asociación entre un gen y una función.
 * Contiene el ID de la función y la evidencia opcional del vínculo.
 */
@Data

public class GeneFunctionInDTO {
    private Long functionId;
    private String evidence; // "experimental", "computational", "predicted"
}
