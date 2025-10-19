package com.developers.GenomeBank_Api.models.dto.functionDTOs;

import lombok.Data;

/**
 * DTO de entrada para la creación de una nueva función biológica.
 */
@Data
public class CreateFunctionInDTO {
    private String code;
    private String name;
    private String category;
}
