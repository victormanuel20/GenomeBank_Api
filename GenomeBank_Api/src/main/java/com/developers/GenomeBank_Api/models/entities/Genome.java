package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un genoma dentro del sistema GenomeBank.
 * Un genoma agrupa un conjunto de cromosomas pertenecientes a una Species.
 * Se corresponde con la tabla "genomes" en la base de datos.
 */
@Entity
@Table(name = "genome")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Genome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="version")
    private String version;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;

    /*@OneToMany(mappedBy = "genome")
    private Set<Chromosome> chromosomes = new HashSet<>();*/
}
