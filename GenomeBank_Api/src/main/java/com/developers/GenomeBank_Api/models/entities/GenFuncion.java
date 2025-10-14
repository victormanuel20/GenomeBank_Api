package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gen_funcion")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenFuncion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gen_id")
    private Gen gen;

    @ManyToOne
    @JoinColumn(name = "funcion_id")
    private Funcion funcion;

    @Column(name = "evidencia")
    private String evidencia;
}
