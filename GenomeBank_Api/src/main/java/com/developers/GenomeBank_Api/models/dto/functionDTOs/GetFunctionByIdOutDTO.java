package com.developers.GenomeBank_Api.models.dto.functionDTOs;

import lombok.Data;

/**
 * DTO de salida para obtener una función por su ID.
 */
@Data
public class GetFunctionByIdOutDTO {
    private String name;
    private String category;
}
