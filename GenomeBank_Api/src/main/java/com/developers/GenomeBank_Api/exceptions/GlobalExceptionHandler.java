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

    @ExceptionHandler(SpeciesNotUpdatedException.class)
    public ResponseEntity<Map<String, Object>> handleNotUpdatedSpecies(SpeciesNotUpdatedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", "Error al actualizar especie",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(SpeciesNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundSpecies(SpeciesNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", "Error al eliminar especie",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(SpeciesNotCreatedException.class)
    public ResponseEntity<Map<String, Object>> handleNotCreated(SpeciesNotCreatedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", "Error al crear especie",
                        "message", ex.getMessage()
                ));
    }

}
