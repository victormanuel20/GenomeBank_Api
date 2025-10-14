package com.developers.GenomeBank_Api.repositorios;

import com.developers.GenomeBank_Api.models.entities.Genoma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenomaRepository extends JpaRepository<Genoma, Long> {
}
