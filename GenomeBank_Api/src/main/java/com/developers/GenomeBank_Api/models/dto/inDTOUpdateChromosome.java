package com.developers.GenomeBank_Api.models.dto;

public class inDTOUpdateChromosome {
    private String name;
    private int length;
    private String sequence;

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    public String getSequence() { return sequence; }
    public void setSequence(String sequence) { this.sequence = sequence; }
}
