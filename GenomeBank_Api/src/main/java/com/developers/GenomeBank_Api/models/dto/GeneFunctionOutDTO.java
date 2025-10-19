package com.developers.GenomeBank_Api.models.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GeneFunctionOutDTO {
    private Long id;
    private Long functionId;
    private String functionCode;
    private String functionName;
    private String category;
    private String evidence;
    private LocalDateTime associatedAt;
}