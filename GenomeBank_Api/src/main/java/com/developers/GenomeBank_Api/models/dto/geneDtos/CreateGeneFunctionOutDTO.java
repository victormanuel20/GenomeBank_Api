package com.developers.GenomeBank_Api.models.dto.geneDtos;

import com.developers.GenomeBank_Api.models.dto.ResultDTO;
import lombok.Data;

@Data
/**
 * DTO para la respuesta al crear una asociación entre un gen y una función.
 */
public class CreateGeneFunctionOutDTO extends ResultDTO {
    private Long geneFunctionId;


    }
