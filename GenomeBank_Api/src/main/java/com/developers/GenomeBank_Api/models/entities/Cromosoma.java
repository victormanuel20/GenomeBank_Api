package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cromosoma")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cromosoma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="longitud")
    private Integer longitud;

    @Column(name="secuencia")
    private String secuencia;

    @ManyToOne
    @JoinColumn(name = "genoma_id")
    private Genoma genoma;

    /*@OneToMany(mappedBy = "cromosoma")
    private Set<Gen> genes = new HashSet<>();*/
}
