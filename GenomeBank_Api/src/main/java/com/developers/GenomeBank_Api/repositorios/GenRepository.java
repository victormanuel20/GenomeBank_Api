package com.developers.GenomeBank_Api.repositorios;

import com.developers.GenomeBank_Api.models.entities.Gen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenRepository extends JpaRepository<Gen, Long> {
}
