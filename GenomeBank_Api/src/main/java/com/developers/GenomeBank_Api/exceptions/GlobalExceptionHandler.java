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

    @ExceptionHandler(DuplicateSpeciesException.class)
    public ResponseEntity<Map<String, Object>> handleNotCreated(DuplicateSpeciesException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", "Error al crear especie",
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
                        "message", "Cannot delete because it is related with other entities"
                        ));}

    @ExceptionHandler(InvalidSpeciesFilterException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidSpeciesFilter(InvalidSpeciesFilterException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // ✅ 400 (filtro inválido)
                .body(Map.of(
                        "error", "Invalid species filter",
                        "message", ex.getMessage()
                ));
    }


}
