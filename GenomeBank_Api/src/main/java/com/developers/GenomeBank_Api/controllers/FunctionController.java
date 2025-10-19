package com.developers.GenomeBank_Api.controllers;

import com.developers.GenomeBank_Api.models.dto.functionDTOs.*;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.DeleteFunctionOutDTO;
import com.developers.GenomeBank_Api.services.IFunctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de funciones.
 * Proporciona los endpoints requeridos en el proyecto para las funciones.
 * La seguridad de los endpoints se gestiona mediante anotaciones @PreAuthorize para roles específicos.
 */
@RestController
@RequestMapping("/functions")
public class FunctionController {
    private final IFunctionService functionService;

    public FunctionController(IFunctionService functionService) {
        this.functionService = functionService;
    }

    /**
     * Endpoint para listar funciones biológicas.
     * Puede listar todas o aplicar filtros por código o categoría.
     * (required = false) quiere decir que no es obligatorio ponerlo
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<GetFunctionOutDTO>> getFunctions(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String category) {

        List<GetFunctionOutDTO> functions = functionService.getFunctions(code, category);
        return ResponseEntity.ok(functions);
    }

    /**
     * Endpoint para obtener función por id
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<GetFunctionByIdOutDTO> getFunctionById(@PathVariable Long id) {
        GetFunctionByIdOutDTO function = functionService.getFunctionById(id);
        return ResponseEntity.ok(function);
    }

    /**
     * Endpoint para crear función
     * @param createFunctionInDTO
     * @return
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')") // opcional si estás usando seguridad
    public ResponseEntity<CreateFunctionOutDTO> createFunction(@RequestBody CreateFunctionInDTO createFunctionInDTO) {
        CreateFunctionOutDTO response = functionService.createFunction(createFunctionInDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateFunctionOutDTO> updateFunction(
            @PathVariable Long id,
            @RequestBody UpdateFunctionInDTO updateFunctionInDTO) {

        UpdateFunctionOutDTO result = functionService.updateFunction(id, updateFunctionInDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * Elimina la function con DTO
     * @param id
     * @return ResponseEntity
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteFunctionOutDTO> deleteFunction(@PathVariable Long id) {
        return ResponseEntity.ok(this.functionService.deleteFunction(id));
    }

}
