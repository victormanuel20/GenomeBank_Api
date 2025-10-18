package com.developers.GenomeBank_Api.models.dto;

public class outDTOCreateChromosome {

    private Long id;  // ID del cromosoma
    private String name;  // Nombre del cromosoma
    private int length;   // Longitud del cromosoma
    private String sequence;  // Secuencia de ADN
    private GenomeDTO genome;  // Información del genoma

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public GenomeDTO getGenome() {
        return genome;
    }

    public void setGenome(GenomeDTO genome) {
        this.genome = genome;
    }

    // Sub-DTO para el Genoma
    public static class GenomeDTO {
        private Long id;
        private String version;

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
