package com.developers.GenomeBank_Api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * Manejador global de excepciones personalizadas.
 * Retorna respuestas JSON legibles para Postman o clientes HTTP.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidEmail(InvalidEmailException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", "Invalid email",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(GenomeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleGenomeNotFound(GenomeNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "Genome not found",
                        "message", ex.getMessage()
                ));
    }


    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(org.springframework.dao.DataIntegrityViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(Map.of(
                        "error", "Delete conflict",
                        "message", "Cannot delete genome because it has related chromosomes or genes."
                ));
    }





}
