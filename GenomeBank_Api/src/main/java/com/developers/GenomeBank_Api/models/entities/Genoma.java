package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "genoma")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Genoma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @Column(name="version")
    private String version;

    @ManyToOne
    @JoinColumn(name = "especie_id")
    private Especie especie;

    /*@OneToMany(mappedBy = "genoma")
    private Set<Cromosoma> cromosomas = new HashSet<>();*/
}
