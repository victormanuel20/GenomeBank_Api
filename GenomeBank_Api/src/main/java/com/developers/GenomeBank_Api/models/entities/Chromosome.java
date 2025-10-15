package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un cromosoma dentro de un genoma.
 * Cada cromosoma pertenece a un Genome y contiene múltiples Gene genes.
 * Esta clase se mapea a la tabla "chromosomes" en la base de datos.
 */
@Entity
@Table(name = "chromosome")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chromosome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="length")
    private Integer length;

    @Column(name="sequence")
    private String sequence;

    @ManyToOne
    @JoinColumn(name = "genome_id")
    private Genome genome;

    /*@OneToMany(mappedBy = "chromosome")
    private Set<Gene> genes = new HashSet<>();*/
}
