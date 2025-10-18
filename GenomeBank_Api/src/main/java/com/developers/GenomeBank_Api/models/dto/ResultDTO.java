package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;

/**
 * Base DTO used for API responses to indicate success or failure.
 */
@Data
public class ResultDTO {
    private boolean isSuccess;    // true if the operation was successful
    private String ErrorMessage;   // contains the error message if it failed
}
