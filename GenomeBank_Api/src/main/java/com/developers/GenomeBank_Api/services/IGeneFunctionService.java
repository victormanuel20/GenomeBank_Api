package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.entities.GeneFunction;

import java.util.Set;

/**
 * Interfaz de servicio para la relación entre genes y funciones.
 * Contiene los métodos que permiten establecer, consultar o eliminar asociaciones
 * entre las entidades Gene y Function.
 */
public interface IGeneFunctionService {
    public void createGeneFunctionBatch(Set<GeneFunction> functionGene);
}
