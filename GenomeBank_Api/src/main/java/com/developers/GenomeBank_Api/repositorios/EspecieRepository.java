package com.developers.GenomeBank_Api.repositorios;

import com.developers.GenomeBank_Api.models.entities.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {
}
