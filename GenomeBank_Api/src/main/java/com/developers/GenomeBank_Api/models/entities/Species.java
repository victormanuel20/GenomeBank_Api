package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una especie biológica registrada en GenomeBank.
 * Cada especie puede estar asociada a uno o varios genomas.
 * Incluye información como el nombre científico y el nombre común.
 * Mapeada a la tabla "species" en la base de datos.
 */
@Entity
@Table(name = "species")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="scientific_name")
    private String scientificName;

    @Column(name="common_name")
    private String commonName;

    /*@OneToMany(mappedBy = "species")
    private Set<Genome> genomes = new HashSet<>();*/
}
