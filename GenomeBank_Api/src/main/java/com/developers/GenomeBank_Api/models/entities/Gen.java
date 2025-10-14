package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gen")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Gen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="simbolo")
    private String simbolo;

    @Column(name="inicio")
    private Integer posicionInicio;

    @Column(name="fin")
    private Integer posicionFin;

    @Column(name="hebra")
    private String hebra;

    @Column(name="secuencia")
    private String secuencia;

    @ManyToOne
    @JoinColumn(name = "cromosoma_id")
    private Cromosoma cromosoma;

    /*@OneToMany(mappedBy = "gen")
    private Set<GenFuncion> genFunciones = new HashSet<>();*/
}
