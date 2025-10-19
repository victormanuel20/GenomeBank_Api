package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa un gen dentro de un cromosoma.
 * Cada gen pertenece a un Chromosome y puede tener múltiples funciones
 * asociadas a través de la entidad intermedia GeneFunction.
 * Se almacena en la tabla "genes" de la base de datos.
 */
@Entity
@Table(name = "gene")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Gene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="symbol")
    private String symbol;

    @Column(name="start_position")
    private Integer startPosition;

    @Column(name="end_position")
    private Integer endPosition;

    @Column(name="strand")
    private String strand;

    @Column(name="sequence")
    private String sequence;

    @ManyToOne
    @JoinColumn(name = "chromosome_id")
    private Chromosome chromosome;

    @OneToMany(mappedBy = "gene")
    private Set<GeneFunction> geneFunctions = new HashSet<>();
}
