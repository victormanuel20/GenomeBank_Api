package com.developers.GenomeBank_Api.controllers;

import com.developers.GenomeBank_Api.models.dto.genome.*;
import com.developers.GenomeBank_Api.services.IGenomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de genomas.
 * Proporciona los endpoints requeridos en el proyecto para los genomas.
 * La seguridad de los endpoints se gestiona mediante anotaciones @PreAuthorize para roles específicos.
 */
@RestController
@RequestMapping("/genomes")
public class GenomeController {

    private final IGenomeService genomeService;

    public GenomeController(IGenomeService genomeService) {
        this.genomeService = genomeService;
    }

    /**
     * Endpoint to create a new Genome.
     * Accessible only to users with ADMIN role.
     *
     * @param createGenomeInDTO input data (version and speciesId)
     * @return response containing success or error details
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateGenomeOutDTO> createGenome(@RequestBody CreateGenomeInDTO createGenomeInDTO) {

        CreateGenomeOutDTO response = this.genomeService.createGenome(createGenomeInDTO);

        if (!response.isSucess()) {

            return ResponseEntity.badRequest().body(response); // HTTP 400
        }

        return ResponseEntity.status(201).body(response);


    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteGenomeOutDTO> deleteGenome(@PathVariable Long id) {

        DeleteGenomeOutDTO response = this.genomeService.deleteGenome(id);

        if (!response.isSucess()) {

            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }


    /**
     * Lista todos los genomas o filtra por especie si se proporciona speciesId.
     *
     * Ejemplos:
     * - GET /genomes → Lista todos los genomas
     * - GET /genomes?speciesId=1 → Lista solo los genomas de la especie 1
     *
     * @param speciesId (opcional) ID de la especie para filtrar
     * @return ResponseEntity con lista de GenomeOutDTO
     */
    @GetMapping("")
    public ResponseEntity<List<GenomeOutDTO>> getGenomes(
            @RequestParam(required = false) Long speciesId) {

        // Construir el InDTO con el query param
        GetGenomesInDTO inDTO = new GetGenomesInDTO();
        inDTO.setSpeciesId(speciesId);

        // Llamar al servicio (lanzará InvalidSpeciesFilterException si no existe)
        List<GenomeOutDTO> response = genomeService.getGenomes(inDTO);

        // Retornar HTTP 200 con la lista
        return ResponseEntity.ok(response);
    }

    /**
     * Consulta un genoma específico por su ID.
     *
     * @param id ID del genoma a consultar
     * @return ResponseEntity con GetGenomeByIdOutDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetGenomeByIdOutDTO> getGenomeById(@PathVariable Long id) {

        // Construir el InDTO
        GetGenomeByIdInDTO inDTO = new GetGenomeByIdInDTO();
        inDTO.setId(id);

        // Llamar al servicio (si no existe, lanzará GenomeNotFoundException)
        GetGenomeByIdOutDTO response = genomeService.getGenomeById(inDTO);

        // Respuesta exitosa
        return ResponseEntity.ok(response); // 200
    }

    /**
     * Actualiza un genoma existente por su ID.
     * Solo usuarios con rol ADMIN pueden acceder.
     * Todos los campos son requeridos (version, speciesId).
     *
     * @param id ID del genoma a actualizar
     * @param inDTO datos actualizados del genoma
     * @return ResponseEntity con UpdateGenomeOutDTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateGenomeOutDTO> updateGenome(
            @PathVariable Long id,
            @RequestBody UpdateGenomeInDTO inDTO) {

        // Llamar al servicio (validaciones manuales dentro del service)
        UpdateGenomeOutDTO response = genomeService.updateGenome(id, inDTO);

        // Retornar respuesta exitosa
        return ResponseEntity.ok(response); // 200
    }



}
