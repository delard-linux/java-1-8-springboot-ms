package com.empresa.gestion.controller;

import com.empresa.gestion.dto.SedeDTO;
import com.empresa.gestion.service.SedeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para gestión de Sedes
 * 
 * ENDPOINTS CRUD:
 * - GET    /api/sedes             -> Listar todas
 * - GET    /api/sedes/{id}        -> Obtener una por ID
 * - POST   /api/sedes             -> Crear nueva
 * - PUT    /api/sedes/{id}        -> Actualizar existente
 * - DELETE /api/sedes/{id}        -> Eliminar
 * 
 * JAVA 21 + SPRING BOOT 3.3:
 * - jakarta.validation.* (Jakarta Bean Validation)
 */
@RestController
@RequestMapping("/api/sedes")
@Validated
public class SedeController {

    private static final Logger logger = LoggerFactory.getLogger(SedeController.class);

    private final SedeService sedeService;

    public SedeController(SedeService sedeService) {
        this.sedeService = sedeService;
    }

    /**
     * Listar todas las sedes
     * GET /api/sedes
     */
    @GetMapping
    public ResponseEntity<List<SedeDTO>> listarTodas() {
        logger.info("GET /api/sedes - Listar todas las sedes");
        List<SedeDTO> sedes = sedeService.obtenerTodas();
        return ResponseEntity.ok(sedes);
    }

    /**
     * Obtener una sede por ID
     * GET /api/sedes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<SedeDTO> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /api/sedes/{} - Obtener sede", id);
        
        return sedeService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear una nueva sede
     * POST /api/sedes
     */
    @PostMapping
    public ResponseEntity<SedeDTO> crear(@Valid @RequestBody SedeDTO sedeDTO) {
        logger.info("POST /api/sedes - Crear sede: {}", sedeDTO.getNombre());
        
        try {
            SedeDTO sedeCreada = sedeService.crear(sedeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(sedeCreada);
        } catch (IllegalArgumentException e) {
            logger.error("Error al crear sede: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualizar una sede existente
     * PUT /api/sedes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<SedeDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SedeDTO sedeDTO) {
        
        logger.info("PUT /api/sedes/{} - Actualizar sede", id);
        
        try {
            SedeDTO sedeActualizada = sedeService.actualizar(id, sedeDTO);
            return ResponseEntity.ok(sedeActualizada);
        } catch (IllegalArgumentException e) {
            logger.error("Error al actualizar sede: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar una sede
     * DELETE /api/sedes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/sedes/{} - Eliminar sede", id);
        
        try {
            sedeService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error al eliminar sede: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener sedes de una empresa específica
     * GET /api/sedes/empresa/{empresaId}
     */
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<SedeDTO>> listarPorEmpresa(@PathVariable Long empresaId) {
        logger.info("GET /api/sedes/empresa/{} - Listar sedes de empresa", empresaId);
        List<SedeDTO> sedes = sedeService.obtenerPorEmpresa(empresaId);
        return ResponseEntity.ok(sedes);
    }

    /**
     * Buscar sedes por ciudad
     * GET /api/sedes/ciudad/{ciudad}
     */
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<SedeDTO>> buscarPorCiudad(@PathVariable String ciudad) {
        logger.info("GET /api/sedes/ciudad/{} - Buscar por ciudad", ciudad);
        List<SedeDTO> sedes = sedeService.buscarPorCiudad(ciudad);
        return ResponseEntity.ok(sedes);
    }

    /**
     * Buscar sedes por provincia
     * GET /api/sedes/provincia/{provincia}
     */
    @GetMapping("/provincia/{provincia}")
    public ResponseEntity<List<SedeDTO>> buscarPorProvincia(@PathVariable String provincia) {
        logger.info("GET /api/sedes/provincia/{} - Buscar por provincia", provincia);
        List<SedeDTO> sedes = sedeService.buscarPorProvincia(provincia);
        return ResponseEntity.ok(sedes);
    }

    /**
     * Obtener sede principal de una empresa
     * GET /api/sedes/empresa/{empresaId}/principal
     */
    @GetMapping("/empresa/{empresaId}/principal")
    public ResponseEntity<SedeDTO> obtenerSedePrincipal(@PathVariable Long empresaId) {
        logger.info("GET /api/sedes/empresa/{}/principal - Obtener sede principal", empresaId);
        
        return sedeService.obtenerSedePrincipal(empresaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Buscar sedes por nombre
     * GET /api/sedes/buscar?texto=...
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<SedeDTO>> buscarPorNombre(@RequestParam String texto) {
        logger.info("GET /api/sedes/buscar?texto={}", texto);
        List<SedeDTO> sedes = sedeService.buscarPorNombre(texto);
        return ResponseEntity.ok(sedes);
    }

    /**
     * Contar sedes de una empresa
     * GET /api/sedes/empresa/{empresaId}/count
     */
    @GetMapping("/empresa/{empresaId}/count")
    public ResponseEntity<Long> contarSedesPorEmpresa(@PathVariable Long empresaId) {
        logger.info("GET /api/sedes/empresa/{}/count", empresaId);
        long count = sedeService.contarSedesPorEmpresa(empresaId);
        return ResponseEntity.ok(count);
    }
}

