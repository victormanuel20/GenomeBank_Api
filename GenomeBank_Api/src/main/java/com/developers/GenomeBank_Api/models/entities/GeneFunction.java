package com.developers.GenomeBank_Api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad intermedia que representa la relación entre Gene y Function.
 * Esta clase define la asociación muchos a muchos entre genes y funciones,
 * y permite agregar información adicional sobre la relación si es necesario.
 * Se mapea con la tabla "gene_functions" en la base de datos.
 */
@Entity
@Table(name = "gene_function")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeneFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment en MySQL
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gene_id")
    private Gene gene;

    @ManyToOne
    @JoinColumn(name = "function_id")
    private Function function;

    @Column(name = "evidence")
    private String evidence;
}
