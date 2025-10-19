package com.developers.GenomeBank_Api.models.dto.functionDTOs;

import lombok.Data;

/**
 * Información de salida
 * consulta de funciones
 */
@Data
public class GetFunctionOutDTO {
    private String name;
    private String category;
}
