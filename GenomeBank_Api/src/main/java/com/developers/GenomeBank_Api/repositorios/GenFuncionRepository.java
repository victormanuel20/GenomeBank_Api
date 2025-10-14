package com.developers.GenomeBank_Api.repositorios;

import com.developers.GenomeBank_Api.models.entities.GenFuncion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenFuncionRepository extends JpaRepository<GenFuncion, Long> {
}
