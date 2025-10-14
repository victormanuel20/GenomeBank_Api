package com.developers.GenomeBank_Api.repositorios;

import com.developers.GenomeBank_Api.models.entities.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionRespository extends JpaRepository<Funcion, Long> {
}
