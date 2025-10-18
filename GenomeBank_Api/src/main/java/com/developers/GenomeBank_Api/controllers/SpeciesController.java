package com.developers.GenomeBank_Api.controllers;

import com.developers.GenomeBank_Api.models.dto.speciesDtos.CreateSpeciesInDTO;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.CreateSpeciesOutDTO;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.GetSpeciesByIdOutDTO;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.GetAllSpeciesOutDTO;
import com.developers.GenomeBank_Api.services.ISpeciesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de especies.
 * Proporciona los endpoints requeridos en el proyecto para las especies.
 * La seguridad de los endpoints se gestiona mediante anotaciones @PreAuthorize para roles específicos.
 */
@RestController
@RequestMapping("/species")
public class SpeciesController {
    /**
     * Se llama el servicio de especies
     * para obtener el resultado de la lógica de negocio
     */
    private final ISpeciesService speciesService;

    /**
     * Se inyecta el servicio de especies
     * para hacer uso de él
     */
    public SpeciesController(ISpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    /**
     * Obtiene la lista de todos las especies.
     * Solo usuarios con el rol USER y ADMIN pueden acceder.
     * @return ResponseEntity con la lista de especies
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<GetAllSpeciesOutDTO>> getAllSpecies() {
        List<GetAllSpeciesOutDTO> species = speciesService.getAllSpecies();
        return ResponseEntity.ok(species);
    }

    /**
     * Obtiene la especie por ID.
     * Solo usuarios con el rol USER y ADMIN pueden acceder.
     * @return ResponseEntity con la especie
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<GetSpeciesByIdOutDTO> getSpeciesById(@PathVariable Long id) {
        GetSpeciesByIdOutDTO species = speciesService.getSpeciesById(id);
        return ResponseEntity.ok(species);
    }

    /**
     * Crea la especie con DTO
     * @param createSpeciesInDTO
     * @return ResponseEntity
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<CreateSpeciesOutDTO> createSpecies(@RequestBody CreateSpeciesInDTO
                                                                      createSpeciesInDTO) {

        return ResponseEntity.ok(this.speciesService.createSpecies(createSpeciesInDTO));
    }

}
