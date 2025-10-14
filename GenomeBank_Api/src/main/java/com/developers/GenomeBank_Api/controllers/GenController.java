package com.developers.GenomeBank_Api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la gestión de genes.
 * Proporciona los endpoints requeridos en el proyecto para los genes.
 * La seguridad de los endpoints se gestiona mediante anotaciones @PreAuthorize para roles específicos.
 */
@RestController
@RequestMapping("/genes")
public class GenController {
}
