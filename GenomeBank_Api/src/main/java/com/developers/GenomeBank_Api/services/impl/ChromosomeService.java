package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.models.dto.*;
import com.developers.GenomeBank_Api.models.entities.Chromosome;
import com.developers.GenomeBank_Api.models.entities.Genome;
import com.developers.GenomeBank_Api.repositories.ChromosomeRepository;
import com.developers.GenomeBank_Api.repositories.GenomeRepository;
import com.developers.GenomeBank_Api.services.IChromosomeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de cromosomas.
 * Proporciona la lógica de negocio para la gestión de cromosomas, incluyendo operaciones CRUD.
 * Utiliza CromosomaRepository para el acceso a datos.
 */
@Service
public class ChromosomeService implements IChromosomeService {

    private final ChromosomeRepository chromosomeRepository;
    private final GenomeRepository genomeRepository;

    // Constructor para inyectar el repositorio manualmente
    public ChromosomeService(ChromosomeRepository chromosomeRepository, GenomeRepository genomeRepository) {
        this.chromosomeRepository = chromosomeRepository;
        this.genomeRepository = genomeRepository;  // Asegúrate de que sea genomeRepository y no GenomeRpository
    }

    @Override
    public outDTOListChromosome getAllChromosomes(Long genomeId) {
        List<Chromosome> chromosomes;

        try {
            // Filtrar cromosomas por genomeId si se proporciona, de lo contrario, devolver todos los cromosomas
            if (genomeId != null) {
                chromosomes = chromosomeRepository.findByGenomeId(genomeId);
            } else {
                chromosomes = chromosomeRepository.findAll();
            }

            // Convertir la lista de cromosomas a DTOs
            List<outDTOListChromosome.ChromosomeDTO> chromosomeDTOs = chromosomes.stream().map(chromosome -> {
                outDTOListChromosome.ChromosomeDTO dto = new outDTOListChromosome.ChromosomeDTO();
                dto.setId(chromosome.getId());
                dto.setName(chromosome.getName());
                dto.setLength(chromosome.getLength());
                dto.setSequence(chromosome.getSequence());

                // Asignar el genoma al DTO
                outDTOListChromosome.ChromosomeDTO.GenomeDTO genomeDTO = new outDTOListChromosome.ChromosomeDTO.GenomeDTO();
                genomeDTO.setId(chromosome.getGenome().getId());
                genomeDTO.setVersion(chromosome.getGenome().getVersion());

                // Asignar la información de species
                outDTOListChromosome.ChromosomeDTO.GenomeDTO.SpeciesDTO speciesDTO = new outDTOListChromosome.ChromosomeDTO.GenomeDTO.SpeciesDTO();
                speciesDTO.setId(chromosome.getGenome().getSpecies().getId());
                speciesDTO.setScientificName(chromosome.getGenome().getSpecies().getScientificName());
                speciesDTO.setCommonName(chromosome.getGenome().getSpecies().getCommonName());
                genomeDTO.setSpecies(speciesDTO);

                dto.setGenome(genomeDTO);

                return dto;
            }).collect(Collectors.toList());

            // Devolver la lista de cromosomas envuelta en un objeto OutDTOListChromosome
            outDTOListChromosome outDTOList = new outDTOListChromosome();
            outDTOList.setChromosomes(chromosomeDTOs);
            outDTOList.setSuccess(true); // Success = true si todo salió bien
            outDTOList.setErrorMessage(null); // No hay error
            return outDTOList;
        } catch (Exception e) {
            outDTOListChromosome outDTOList = new outDTOListChromosome();
            outDTOList.setSuccess(false); // Success = false si hay un error
            outDTOList.setErrorMessage("Error fetching chromosomes: " + e.getMessage());
            return outDTOList;
        }
    }


    @Override
    public outDTOConsultSpecificChromosome getChromosomeById(Long id) {
        // Buscar el cromosoma por ID
        Optional<Chromosome> chromosomeOpt = chromosomeRepository.findById(id);
        if (!chromosomeOpt.isPresent()) {
            outDTOConsultSpecificChromosome dto = new outDTOConsultSpecificChromosome();
            dto.setSuccess(false);  // Success = false si no se encuentra el cromosoma
            dto.setErrorMessage("Chromosome not found with ID: " + id); // Mensaje de error
            return dto;
        }

        Chromosome chromosome = chromosomeOpt.get();
        outDTOConsultSpecificChromosome dto = new outDTOConsultSpecificChromosome();
        dto.setId(chromosome.getId());
        dto.setName(chromosome.getName());
        dto.setLength(chromosome.getLength());
        dto.setSequence(chromosome.getSequence());

        // Asignar el genoma al DTO
        outDTOConsultSpecificChromosome.GenomeDTO genomeDTO = new outDTOConsultSpecificChromosome.GenomeDTO();
        genomeDTO.setId(chromosome.getGenome().getId());
        genomeDTO.setVersion(chromosome.getGenome().getVersion());

        // Asignar la especie (species) al GenomeDTO
        outDTOConsultSpecificChromosome.GenomeDTO.SpeciesDTO speciesDTO = new outDTOConsultSpecificChromosome.GenomeDTO.SpeciesDTO();
        speciesDTO.setId(chromosome.getGenome().getSpecies().getId());
        speciesDTO.setScientificName(chromosome.getGenome().getSpecies().getScientificName());
        speciesDTO.setCommonName(chromosome.getGenome().getSpecies().getCommonName());
        genomeDTO.setSpecies(speciesDTO);

        dto.setGenome(genomeDTO);
        dto.setSuccess(true); // Aseguramos que success sea `true`
        dto.setErrorMessage(null);

        return dto;
    }



    @Override
    public outDTOCreateChromosome createChromosome(inDTOCreateChromosome chromosomeInDTO) {
        try {
            // Crear un nuevo cromosoma con los datos del DTO recibido
            Chromosome chromosome = new Chromosome();
            chromosome.setName(chromosomeInDTO.getName());
            chromosome.setLength(chromosomeInDTO.getLength());
            chromosome.setSequence(chromosomeInDTO.getSequence());

            // Obtener el genoma con el ID proporcionado en el DTO
            Genome genome = genomeRepository.findById(chromosomeInDTO.getGenomeId())
                    .orElseThrow(() -> new RuntimeException("Genome not found with ID: " + chromosomeInDTO.getGenomeId()));

            chromosome.setGenome(genome);

            // Guardar el cromosoma en la base de datos
            Chromosome savedChromosome = chromosomeRepository.save(chromosome);

            // Convertir el cromosoma guardado a un DTO y devolverlo
            outDTOCreateChromosome dto = new outDTOCreateChromosome();
            dto.setId(savedChromosome.getId());
            dto.setName(savedChromosome.getName());
            dto.setLength(savedChromosome.getLength());
            dto.setSequence(savedChromosome.getSequence());

            // Asignar el genoma al DTO
            outDTOCreateChromosome.GenomeDTO genomeDTO = new outDTOCreateChromosome.GenomeDTO();
            genomeDTO.setId(savedChromosome.getGenome().getId());
            genomeDTO.setVersion(savedChromosome.getGenome().getVersion());
            dto.setGenome(genomeDTO);
            dto.setSuccess(true); // Aseguramos que success sea `true`
            dto.setErrorMessage(null); // No hay error

            return dto;
        } catch (Exception e) {
            outDTOCreateChromosome dto = new outDTOCreateChromosome();
            dto.setSuccess(false); // Success = false si hay un error
            dto.setErrorMessage("Error creating chromosome: " + e.getMessage());
            return dto;
        }
    }




    @Override
    @Transactional
    public outDTOUpdateChromosome updateChromosome(Long id, inDTOUpdateChromosome dto) {
        try {
            Chromosome entity = chromosomeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Chromosome not found: " + id));

            // PUT = reemplazo completo (si quieres hacer PATCH, validar nulls)
            entity.setName(dto.getName());
            entity.setLength(dto.getLength());
            entity.setSequence(dto.getSequence());

            Chromosome updated = chromosomeRepository.save(entity);

            // Map a outDTO
            outDTOUpdateChromosome out = new outDTOUpdateChromosome();
            out.setId(updated.getId());
            out.setName(updated.getName());
            out.setLength(updated.getLength());
            out.setSequence(updated.getSequence());

            outDTOUpdateChromosome.GenomeDTO g = new outDTOUpdateChromosome.GenomeDTO();
            g.setId(updated.getGenome().getId());
            g.setVersion(updated.getGenome().getVersion());
            out.setGenome(g);

            out.setSuccess(true);  // Success = true si todo va bien
            out.setErrorMessage(null);  // No hay error

            return out;
        } catch (Exception e) {
            outDTOUpdateChromosome out = new outDTOUpdateChromosome();
            out.setSuccess(false); // Success = false si hay un error
            out.setErrorMessage("Error updating chromosome: " + e.getMessage());
            return out;
        }
    }



    @Override
    public void deleteChromosome(Long id) {
        try {
            // Elimina el cromosoma por su ID
            if (chromosomeRepository.existsById(id)) {
                chromosomeRepository.deleteById(id);
                // Aquí no necesitamos un DTO de salida, pero si quieres puedes crear uno.
            } else {
                // Si no existe el cromosoma
                throw new RuntimeException("Chromosome not found with ID: " + id);
            }
        } catch (Exception e) {
            // Manejar el error (se puede lanzar o devolver un error como en otros casos)
            throw new RuntimeException("Error deleting chromosome: " + e.getMessage());
        }
    }





    // NO SE HAN IMPLEMENTADO DE MOMENTO LAS SIGUIENTES (EL CÓDIGO CAMBIARÁ)
    @Override
    public String getChromosomeSequence(Long id) {
        // Obtiene la secuencia de ADN de un cromosoma
        Optional<Chromosome> chromosome = chromosomeRepository.findById(id);
        return chromosome.map(Chromosome::getSequence).orElse(null); // Retorna la secuencia o null si no se encuentra
    }

    @Override
    public String getChromosomeSubSequence(Long id, int start, int end) {
        // Obtiene una subsecuencia del cromosoma entre los índices start y end
        Optional<Chromosome> chromosome = chromosomeRepository.findById(id);
        if (chromosome.isPresent()) {
            String sequence = chromosome.get().getSequence();
            if (start >= 0 && end <= sequence.length() && start < end) {
                return sequence.substring(start, end); // Retorna la subsecuencia
            }
        }
        return null; // Si el cromosoma no se encuentra o el rango no es válido
    }

    @Override
    public void updateChromosomeSequence(Long id, String sequence) {
        // Actualiza la secuencia de ADN de un cromosoma
        Optional<Chromosome> chromosome = chromosomeRepository.findById(id);
        if (chromosome.isPresent()) {
            Chromosome updatedChromosome = chromosome.get();
            updatedChromosome.setSequence(sequence); // Actualiza la secuencia
            chromosomeRepository.save(updatedChromosome); // Guarda el cromosoma actualizado
        }
    }
}
