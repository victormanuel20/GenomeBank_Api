package com.developers.GenomeBank_Api.repositories;

import com.developers.GenomeBank_Api.models.entities.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Function.
 * Gestiona el acceso y la persistencia de las funciones génicas en la base de datos.
 */
@Repository
public interface FunctionRespository extends JpaRepository<Function, Long> {
}
