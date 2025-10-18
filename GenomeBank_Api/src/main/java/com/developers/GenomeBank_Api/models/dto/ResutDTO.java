package com.developers.GenomeBank_Api.models.dto;


import lombok.Data;

/**
 * Clase para devolver el resultado de la petición en
 * JSON
 */
@Data
public class ResutDTO {
    private boolean success;
    private String errorMessage;

}
