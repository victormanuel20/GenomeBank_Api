package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad que representa una función genética asociada a uno o varios genes.
 * Cada función describe el rol biológico o molecular de un gen dentro de un organismo.
 * Está relacionada con Gene mediante la tabla intermedia GeneFunction.
 */
@Entity
@Table(name = "`function`")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="category")
    private String category;

    @OneToMany(mappedBy = "function")
    private List<GeneFunction> geneFunctions;

}
