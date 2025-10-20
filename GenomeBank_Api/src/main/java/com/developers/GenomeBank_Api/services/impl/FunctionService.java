package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.exceptions.functionExceptions.FunctionInUseException;
import com.developers.GenomeBank_Api.exceptions.functionExceptions.FunctionNotCreatedException;
import com.developers.GenomeBank_Api.exceptions.functionExceptions.FunctionNotFoundException;
import com.developers.GenomeBank_Api.exceptions.functionExceptions.FunctionNotUpdatedException;
import com.developers.GenomeBank_Api.exceptions.speciesExceptions.SpeciesNotFoundException;
import com.developers.GenomeBank_Api.models.dto.functionDTOs.*;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.DeleteFunctionOutDTO;
import com.developers.GenomeBank_Api.models.entities.Function;
import com.developers.GenomeBank_Api.models.entities.Species;
import com.developers.GenomeBank_Api.repositories.FunctionRespository;
import com.developers.GenomeBank_Api.services.IFunctionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de funciones.
 * Proporciona la lógica de negocio para la gestión de funciones, incluyendo operaciones CRUD.
 * Utiliza FuncionRepository para el acceso a datos.
 */
@Service
public class FunctionService implements IFunctionService {

    private final FunctionRespository functionRepository;

    public FunctionService(FunctionRespository functionRepository) {
        this.functionRepository = functionRepository;
    }

    @Override
    public List<GetFunctionOutDTO> getFunctions(String code, String category) {
        List<Function> functions;

        if (code != null && !code.isBlank()) {
            functions = functionRepository.findByCode(code);
            if (functions.isEmpty()){
                throw new FunctionNotFoundException("Function not found");
            }
        } else if (category != null && !category.isBlank()) {
            functions = functionRepository.findByCategory(category);
            if (functions.isEmpty()){
                throw new FunctionNotFoundException("Function not found");
            }
        } else {
            functions = functionRepository.findAll();
        }

        // Mapeo de entidades a DTO
        return functions.stream()
                .map(func -> {
                    GetFunctionOutDTO dto = new GetFunctionOutDTO();
                    dto.setName(func.getName());
                    dto.setCategory(func.getCategory());
                    return dto;
                })
                .toList();
    }

    @Override
    public GetFunctionByIdOutDTO getFunctionById(Long id) {
        Function func = functionRepository.findById(id)
                .orElseThrow(() -> new FunctionNotFoundException("Function con ID " + id + " no encontrada"));

        GetFunctionByIdOutDTO dto = new GetFunctionByIdOutDTO();
        dto.setName(func.getName());
        dto.setCategory(func.getCategory());
        return dto;
    }

    @Override
    public CreateFunctionOutDTO createFunction(CreateFunctionInDTO createFunctionInDTO) {
        CreateFunctionOutDTO dto = new CreateFunctionOutDTO();

        if (createFunctionInDTO.getCode() == null || createFunctionInDTO.getCode().isBlank()
                || createFunctionInDTO.getName() == null || createFunctionInDTO.getName().isBlank()
                || createFunctionInDTO.getCategory() == null || createFunctionInDTO.getCategory().isBlank()) {

            throw new FunctionNotCreatedException("Todos los campos son obligatorios");
        }

        boolean exists = !functionRepository.findByCode(createFunctionInDTO.getCode()).isEmpty();
        if (exists) {
            throw new FunctionNotCreatedException("Ya existe una función con el código '" + createFunctionInDTO.getCode());
        }

        Function newFunction = new Function();
        newFunction.setCode(createFunctionInDTO.getCode());
        newFunction.setName(createFunctionInDTO.getName());
        newFunction.setCategory(createFunctionInDTO.getCategory());
        functionRepository.save(newFunction);

        dto.setSucess(true);
        dto.setErrorMessage("Función creada exitosamente");
        return dto;
    }

    @Override
    public UpdateFunctionOutDTO updateFunction(Long id, UpdateFunctionInDTO updateFunctionInDTO) {
        return functionRepository.findById(id)
                .map(functionFound -> {
                    UpdateFunctionOutDTO dto = new UpdateFunctionOutDTO();

                    if (updateFunctionInDTO.getName() == null || updateFunctionInDTO.getName().isBlank() ||
                            updateFunctionInDTO.getCategory() == null || updateFunctionInDTO.getCategory().isBlank() ||
                            updateFunctionInDTO.getCode() == null || updateFunctionInDTO.getCode().isBlank()) {

                        dto.setSucess(false);
                        dto.setErrorMessage("Debe llenar todos los campos");
                        throw new FunctionNotUpdatedException("Error al actualizar función: campos vacíos");
                    }

                    functionFound.setName(updateFunctionInDTO.getName());
                    functionFound.setCategory(updateFunctionInDTO.getCategory());
                    functionFound.setCode(updateFunctionInDTO.getCode());

                    functionRepository.save(functionFound);

                    dto.setSucess(true);
                    dto.setErrorMessage(null);
                    return dto;
                })
                .orElseThrow(() -> new FunctionNotFoundException("Función no encontrada con ID: " + id));
    }

    @Override
    public DeleteFunctionOutDTO deleteFunction(Long id) {
        DeleteFunctionOutDTO dto = new DeleteFunctionOutDTO();

        Function function = functionRepository.findById(id)
                .orElseThrow(() -> new FunctionNotFoundException("Function not found with ID: " + id));

        if (!function.getGeneFunctions().isEmpty()) {
            throw new FunctionInUseException("No se puede eliminar la función porque está asociada a uno o más gene_function");
        }
        functionRepository.delete(function);

        this.functionRepository.delete(function);
        dto.setSucess(true);

        return dto;
    }
    @Override
    public boolean existsById(Long id) {
        return functionRepository.existsById(id);

    }


}
