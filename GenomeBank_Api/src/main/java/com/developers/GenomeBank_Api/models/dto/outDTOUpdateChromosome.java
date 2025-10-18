package com.developers.GenomeBank_Api.models.dto;

public class outDTOUpdateChromosome {
    private Long id;
    private String name;
    private int length;
    private String sequence;
    private GenomeDTO genome;   // resumido (id + version)

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    public String getSequence() { return sequence; }
    public void setSequence(String sequence) { this.sequence = sequence; }

    public GenomeDTO getGenome() { return genome; }
    public void setGenome(GenomeDTO genome) { this.genome = genome; }

    public static class GenomeDTO {
        private Long id;
        private String version;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
    }
}
