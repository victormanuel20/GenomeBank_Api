package com.developers.GenomeBank_Api.services.impl;

import com.developers.GenomeBank_Api.models.dto.chromosomeDto.*;
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

    //Se definen los repositorios para la comunicación con la BD (JPA)
    private final ChromosomeRepository chromosomeRepository;
    private final GenomeRepository genomeRepository;

    // Constructor para inyectar el repositorio manualmente
    public ChromosomeService(ChromosomeRepository chromosomeRepository, GenomeRepository genomeRepository) {
        this.chromosomeRepository = chromosomeRepository;
        this.genomeRepository = genomeRepository;
    }

    @Override
    public outDTOListChromosome getAllChromosomes(Long genomeId) {
        List<Chromosome> chromosomes;

        try {
            // Filtrar cromosomas por genomeId, si no se agrega se devuelve la lista completa
            if (genomeId != null) {
                chromosomes = chromosomeRepository.findByGenomeId(genomeId);
            } else {
                chromosomes = chromosomeRepository.findAll();
            }

            // Convertir la lista de cromosomas a DTOs
            //Este me vuelve la lista de los cromosomas que tenemos en un stream
            //para que esto se pueda manejar mas facil, para temas de organizar, ordenar y recolectar un resultado
            List<outDTOListChromosome.ChromosomeDTO> chromosomeDTOs = chromosomes.stream().map(chromosome -> {
                //Se realiza la clave valor, entre entidad y convierte a un DTO
                outDTOListChromosome.ChromosomeDTO dto = new outDTOListChromosome.ChromosomeDTO();
                dto.setId(chromosome.getId());
                dto.setName(chromosome.getName());
                dto.setLength(chromosome.getLength());
                dto.setSequence(chromosome.getSequence());

                // Asignar el genoma al DTO
                //Se agregan los valores del genoma que se quieren mostrar a la hora de listar los cromosomas
                outDTOListChromosome.ChromosomeDTO.GenomeDTO genomeDTO = new outDTOListChromosome.ChromosomeDTO.GenomeDTO();
                genomeDTO.setId(chromosome.getGenome().getId());
                genomeDTO.setVersion(chromosome.getGenome().getVersion());

                // Asignar la información de species
                //Se agregan los valores del especie que se quieren mostrar a la hora de listar los cromosomas
                outDTOListChromosome.ChromosomeDTO.GenomeDTO.SpeciesDTO speciesDTO = new outDTOListChromosome.ChromosomeDTO.GenomeDTO.SpeciesDTO();
                speciesDTO.setId(chromosome.getGenome().getSpecies().getId());
                speciesDTO.setScientificName(chromosome.getGenome().getSpecies().getScientificName());
                speciesDTO.setCommonName(chromosome.getGenome().getSpecies().getCommonName());
                genomeDTO.setSpecies(speciesDTO);

                dto.setGenome(genomeDTO);

                return dto;
            }).collect(Collectors.toList());//Junta lo trabajado en el Stream, todos los DTO transformados

            // Devolver la lista de cromosomas envuelta en un objeto OutDTOListChromosome
            outDTOListChromosome outDTOList = new outDTOListChromosome();
            outDTOList.setChromosomes(chromosomeDTOs);
            outDTOList.setSuccess(true); // Success = true
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
        // Buscar el cromosoma por ID del genoma
        // Es opcional ya que este valor que da el cliente puede venir nulo, para esto se maneja esta excepcion
        Optional<Chromosome> chromosomeOpt = chromosomeRepository.findById(id);
        if (chromosomeOpt.isEmpty()) {
            outDTOConsultSpecificChromosome dto = new outDTOConsultSpecificChromosome();
            dto.setSuccess(false);
            dto.setErrorMessage("Chromosome not found with ID: " + id); // Mensaje de error
            return dto;
        }

        Chromosome chromosome = chromosomeOpt.get(); //Se obtiene toda la info correspondiente al cromosoma
        outDTOConsultSpecificChromosome dto = new outDTOConsultSpecificChromosome();
        //Se pasan los valores tan cual, los que ya se tienen preestablecidos en el outDTO
        dto.setId(chromosome.getId());
        dto.setName(chromosome.getName());
        dto.setLength(chromosome.getLength());
        dto.setSequence(chromosome.getSequence());

        // Asignar el genoma al DTO
        //Se mapea la relacion de genoma y el gnomaDTO
        outDTOConsultSpecificChromosome.GenomeDTO genomeDTO = new outDTOConsultSpecificChromosome.GenomeDTO();
        genomeDTO.setId(chromosome.getGenome().getId());
        genomeDTO.setVersion(chromosome.getGenome().getVersion());

        // Asignar la especie (species) al GenomeDTO
        // Igual con la especie, se mapea el valor con el que se tiene del DTO
        outDTOConsultSpecificChromosome.GenomeDTO.SpeciesDTO speciesDTO = new outDTOConsultSpecificChromosome.GenomeDTO.SpeciesDTO();
        speciesDTO.setId(chromosome.getGenome().getSpecies().getId());
        speciesDTO.setScientificName(chromosome.getGenome().getSpecies().getScientificName());
        speciesDTO.setCommonName(chromosome.getGenome().getSpecies().getCommonName());
        genomeDTO.setSpecies(speciesDTO);

        dto.setGenome(genomeDTO);
        dto.setSuccess(true);
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
            // Se valida que el ID si se encuentre registrado
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
            dto.setSuccess(true);
            dto.setErrorMessage(null); // No hay error

            return dto;
        } catch (Exception e) {
            outDTOCreateChromosome dto = new outDTOCreateChromosome();
            dto.setSuccess(false);
            dto.setErrorMessage("Error creating chromosome: " + e.getMessage());
            return dto;
        }
    }




    @Override
    @Transactional
    public outDTOUpdateChromosome updateChromosome(Long id, inDTOUpdateChromosome dto) {
        try {
            // Este se actualiza segun el ID proporcionado
            Chromosome entity = chromosomeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Chromosome not found: " + id));


            entity.setName(dto.getName());
            entity.setLength(dto.getLength());
            entity.setSequence(dto.getSequence());

            Chromosome updated = chromosomeRepository.save(entity);


            outDTOUpdateChromosome out = new outDTOUpdateChromosome();
            out.setId(updated.getId());
            out.setName(updated.getName());
            out.setLength(updated.getLength());
            out.setSequence(updated.getSequence());

            outDTOUpdateChromosome.GenomeDTO g = new outDTOUpdateChromosome.GenomeDTO();
            g.setId(updated.getGenome().getId());
            g.setVersion(updated.getGenome().getVersion());
            out.setGenome(g);

            out.setSucess(true);
            out.setErrorMessage(null);  // No hay error

            return out;
        } catch (Exception e) {
            outDTOUpdateChromosome out = new outDTOUpdateChromosome();
            out.setSucess(false);
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

            } else {
                // Si no existe el cromosoma
                throw new RuntimeException("Chromosome not found with ID: " + id);
            }
        } catch (Exception e) {

            throw new RuntimeException("Error deleting chromosome: " + e.getMessage());
        }
    }


    @Override
    public outDTOChromosomeSequence getChromosomeSequence(Long id) {
        //Declaramos el DTO que vamos a devolver
        outDTOChromosomeSequence dto = new outDTOChromosomeSequence();
        // Si de lo recibido, se tiene un ID registrado a un cromosoma, este le mapea el valor, devolviendo su ID y secuencia

        return chromosomeRepository.findById(id)
                .map(ch -> {
                    dto.setId(ch.getId());
                    dto.setSequence(ch.getSequence());
                    dto.setSuccess(true);
                    dto.setErrorMessage(null);
                    return dto;
                })
                // Si el opcional esta vacio, se muestra el mensaje de error
                .orElseGet(() -> {
                    dto.setSuccess(false);
                    dto.setErrorMessage("Chromosome not found with ID: " + id);
                    return dto;
                });
    }


    @Override
    public outDTOChromosomeSubsequence getChromosomeSubSequence(Long id, int start, int end) {
        outDTOChromosomeSubsequence dto = new outDTOChromosomeSubsequence();
        //Siempre se valida que el cromosoma opcional tenga algun valor, en este caso con su ID
        //Si esta asociado funciona

        Optional<Chromosome> opt = chromosomeRepository.findById(id);
        if (opt.isEmpty()) {
            dto.setSuccess(false);
            dto.setErrorMessage("Chromosome not found with ID: " + id);
            return dto;
        }

        Chromosome ch = opt.get(); //Obtenemos un cromosoma real, ya registrado
        String sequence = ch.getSequence(); //Obtenemos la secuencia del ADN perteneciente al cromosoma seleccionado
        int L = (sequence != null) ? sequence.length() : 0;
        //Manejamos la longitud de la secuencia, esta si fuese null, deja el valor de la longitud en 0


        if (sequence == null || L == 0) {
            dto.setSuccess(false); //Verifica que haya una secuencia para el ID seleccionado
            dto.setErrorMessage("Chromosome sequence is empty for ID: " + id);
            return dto;
        }
        if (start < 0 || end < 0) {
            dto.setSuccess(false); //Verifica que lo que se elija sea mayor a 0, no menor o negativo
            dto.setErrorMessage("Start and end must be >= 0.");
            return dto;
        }
        if (start >= end) {
            dto.setSuccess(false); //El inicio no puede ser mayor o igual al final, no tendria sentido
            dto.setErrorMessage("Start must be < end.");
            return dto;
        }
        if (end > L) {
            dto.setSuccess(false);//El indice final no puede superar la longitud maxima de la secuencia
            dto.setErrorMessage("End must be <= sequence length (" + L + ").");
            return dto;
        }

        String sub = sequence.substring(start, end);

        dto.setId(ch.getId());
        dto.setStart(start);
        dto.setEnd(end);
        dto.setTotalLength(L);
        dto.setSubsequence(sub);
        dto.setSuccess(true);
        dto.setErrorMessage(null);
        return dto;
    }


    @Override
    public outDTOUpdateChromosomeSequence updateChromosomeSequence(Long id, inDTOUpdateChromosomeSequence body) {
        outDTOUpdateChromosomeSequence dto = new outDTOUpdateChromosomeSequence();


        if (body == null || body.getSequence() == null) {
            dto.setSuccess(false);
            dto.setErrorMessage("Sequence is required.");
            return dto;
        }
        String seq = body.getSequence().trim();
        if (seq.isEmpty()) {
            dto.setSuccess(false);
            dto.setErrorMessage("Sequence cannot be empty.");
            return dto;
        }

        Chromosome entity = chromosomeRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            dto.setSuccess(false);
            dto.setErrorMessage("Chromosome not found with ID: " + id);
            return dto;
        }

        int oldLen = (entity.getSequence() != null) ? entity.getSequence().length() : 0;

        // Actualizamos secuencia y longitud
        entity.setSequence(seq);
        entity.setLength(seq.length());

        Chromosome saved = chromosomeRepository.save(entity);

        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setOldLength(oldLen);
        dto.setNewLength(saved.getLength());
        dto.setSequence(saved.getSequence());
        dto.setSuccess(true);
        dto.setErrorMessage(null);
        return dto;
    }

    @Override
    public boolean existChromosome(Long id) {
         return chromosomeRepository.existsById(id);

    }

}
