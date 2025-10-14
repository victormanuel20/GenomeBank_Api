package com.developers.GenomeBank_Api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la gestión de cromosomas.
 * Proporciona los endpoints requeridos en el proyecto para los cromosomas.
 * La seguridad de los endpoints se gestiona mediante anotaciones @PreAuthorize para roles específicos.
 */
@RestController
@RequestMapping("/cromosomas")
public class CromosomaController {
}
