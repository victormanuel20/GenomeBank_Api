package com.developers.GenomeBank_Api.models.dto;

import java.util.List;

public class outDTOListChromosome {

    private List<ChromosomeDTO> chromosomes;

    public List<ChromosomeDTO> getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(List<ChromosomeDTO> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public static class ChromosomeDTO {
        private Long id;
        private String name;
        private int length;
        private String sequence;
        private GenomeDTO genome;

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

        // Agregar SpeciesDTO
        public static class GenomeDTO {
            private Long id;
            private String version;
            private SpeciesDTO species;  // Añadir species

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

            public SpeciesDTO getSpecies() {
                return species;
            }

            public void setSpecies(SpeciesDTO species) {
                this.species = species;
            }

            // Sub-DTO para Species
            public static class SpeciesDTO {
                private Long id;
                private String scientificName;
                private String commonName;

                public Long getId() {
                    return id;
                }

                public void setId(Long id) {
                    this.id = id;
                }

                public String getScientificName() {
                    return scientificName;
                }

                public void setScientificName(String scientificName) {
                    this.scientificName = scientificName;
                }

                public String getCommonName() {
                    return commonName;
                }

                public void setCommonName(String commonName) {
                    this.commonName = commonName;
                }
            }
        }
    }
}
