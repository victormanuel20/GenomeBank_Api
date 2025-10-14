package com.developers.GenomeBank_Api.repositorios;

import com.developers.GenomeBank_Api.models.entities.Cromosoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CromosomaRepository extends JpaRepository<Cromosoma, Long> {
}
