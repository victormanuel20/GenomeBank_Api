package com.developers.GenomeBank_Api.models.dto.functionDTOs;

import lombok.Data;

/**
 * DTO de entrada para actualizar una función biológica.
 */
@Data
public class UpdateFunctionInDTO {
    private String name;
    private String category;
    private String code;
}
