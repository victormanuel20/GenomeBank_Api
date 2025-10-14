package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especie")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Especie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="nombre_cientifico")
    private String nomCientifico;

    @Column(name="nombre_comun")
    private String nomComun;

    /*@OneToMany(mappedBy = "especie")
    private Set<Genoma> genomas = new HashSet<>();*/
}
