package com.developers.GenomeBank_Api.services;

import com.developers.GenomeBank_Api.models.dto.*;
import com.developers.GenomeBank_Api.models.entities.Chromosome;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de cromosomas.
 * Define los métodos que permiten realizar operaciones de negocio
 * relacionadas con la entidad Chromosome.
 */
public interface IChromosomeService {

    /**
     * Obtiene todos los cromosomas, o filtra por genoma si se proporciona el genomeId.
     *
     * @param genomeId ID del genoma para filtrar los cromosomas
     * @return Lista de cromosomas
     */
    outDTOListChromosome getAllChromosomes(Long genomeId);

    /**
     * Obtiene un cromosoma específico por su ID.
     *
     * @param id ID del cromosoma
     * @return Cromosoma correspondiente al ID proporcionado
     */
    outDTOConsultSpecificChromosome getChromosomeById(Long id);

    /**
     * Crea un nuevo cromosoma.
     *
     * @param chromosome DTO con los datos del cromosoma a crear
     * @return El cromosoma creado
     */
    outDTOCreateChromosome createChromosome(inDTOCreateChromosome chromosome);


    /**
     * Actualiza un cromosoma existente.
     *
     * @param id ID del cromosoma a actualizar
     * @param chromosome Nuevos datos del cromosoma
     * @return El cromosoma actualizado
     */
    outDTOUpdateChromosome updateChromosome(Long id, inDTOUpdateChromosome chromosome);

    /**
     * Elimina un cromosoma por su ID.
     *
     * @param id ID del cromosoma a eliminar
     */
    void deleteChromosome(Long id);

    /**
     * Obtiene la secuencia de ADN completa de un cromosoma.
     *
     * @param id ID del cromosoma
     * @return Secuencia de ADN del cromosoma
     */
    String getChromosomeSequence(Long id);

    /**
     * Obtiene una subsecuencia de ADN de un cromosoma dentro de un rango específico.
     *
     * @param id ID del cromosoma
     * @param start Posición de inicio del rango
     * @param end Posición final del rango
     * @return Subsecuencia de ADN dentro del rango
     */
    String getChromosomeSubSequence(Long id, int start, int end);

    /**
     * Actualiza la secuencia de ADN de un cromosoma.
     *
     * @param id ID del cromosoma a actualizar
     * @param sequence Nueva secuencia de ADN
     */
    void updateChromosomeSequence(Long id, String sequence);
}
