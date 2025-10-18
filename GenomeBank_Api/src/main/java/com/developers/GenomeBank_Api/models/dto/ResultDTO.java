package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

/**
 * Base DTO used for API responses to indicate success or failure.
 */
@Data
public class ResultDTO {
    private boolean successful;    // true if the operation was successful
    private String errorMessage;   // contains the error message if it failed
}