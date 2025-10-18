package com.developers.GenomeBank_Api.models.dto;

public class inDTOCreateChromosome {

    private String name;  // Nombre del cromosoma
    private int length;   // Longitud del cromosoma
    private String sequence;  // Secuencia de ADN del cromosoma

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
