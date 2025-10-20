package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.functionDTOs.*;
import com.developers.GenomeBank_Api.models.dto.speciesDtos.DeleteFunctionOutDTO;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de funciones génicas.
 * Proporciona las operaciones necesarias para manipular la entidad Function.
 */
public interface IFunctionService {
    List<GetFunctionOutDTO> getFunctions(String code, String category);
    public GetFunctionByIdOutDTO getFunctionById(Long id);
    public CreateFunctionOutDTO createFunction(CreateFunctionInDTO createFunctionInDTO);
    public UpdateFunctionOutDTO updateFunction(Long id, UpdateFunctionInDTO updateFunctionInDTO);
    public boolean existsById(Long function);

    public DeleteFunctionOutDTO deleteFunction(Long id);
}
