package com.empresa.gestion.controller;

import com.empresa.gestion.dto.EmpresaDTO;
import com.empresa.gestion.service.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para gestión de Empresas
 * 
 * ENDPOINTS CRUD:
 * - GET    /api/empresas          -> Listar todas
 * - GET    /api/empresas/{id}     -> Obtener una por ID
 * - POST   /api/empresas          -> Crear nueva
 * - PUT    /api/empresas/{id}     -> Actualizar existente
 * - DELETE /api/empresas/{id}     -> Eliminar
 * 
 * JAVA 21 + SPRING BOOT 3.3:
 * - Sintaxis moderna de Java 21
 * - jakarta.validation.* (Jakarta Bean Validation)
 */
@RestController
@RequestMapping("/api/empresas")
@Validated
public class EmpresaController {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaController.class);

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * Listar todas las empresas
     * GET /api/empresas
     */
    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> listarTodas() {
        logger.info("GET /api/empresas - Listar todas las empresas");
        List<EmpresaDTO> empresas = empresaService.obtenerTodas();
        return ResponseEntity.ok(empresas);
    }

    /**
     * Obtener una empresa por ID
     * GET /api/empresas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> obtenerPorId(@PathVariable Long id) {
        logger.info("GET /api/empresas/{} - Obtener empresa", id);
        
        return empresaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear una nueva empresa
     * POST /api/empresas
     * 
     * Validación: @Valid con jakarta.validation
     */
    @PostMapping
    public ResponseEntity<EmpresaDTO> crear(@Valid @RequestBody EmpresaDTO empresaDTO) {
        logger.info("POST /api/empresas - Crear empresa: {}", empresaDTO.getRazonSocial());
        
        try {
            EmpresaDTO empresaCreada = empresaService.crear(empresaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(empresaCreada);
        } catch (IllegalArgumentException e) {
            logger.error("Error al crear empresa: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualizar una empresa existente
     * PUT /api/empresas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpresaDTO empresaDTO) {
        
        logger.info("PUT /api/empresas/{} - Actualizar empresa", id);
        
        try {
            EmpresaDTO empresaActualizada = empresaService.actualizar(id, empresaDTO);
            return ResponseEntity.ok(empresaActualizada);
        } catch (IllegalArgumentException e) {
            logger.error("Error al actualizar empresa: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar una empresa
     * DELETE /api/empresas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.info("DELETE /api/empresas/{} - Eliminar empresa", id);
        
        try {
            empresaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error al eliminar empresa: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Listar empresas activas
     * GET /api/empresas/activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<EmpresaDTO>> listarActivas() {
        logger.info("GET /api/empresas/activas - Listar empresas activas");
        List<EmpresaDTO> empresas = empresaService.obtenerActivas();
        return ResponseEntity.ok(empresas);
    }

    /**
     * Buscar por CIF
     * GET /api/empresas/cif/{cif}
     */
    @GetMapping("/cif/{cif}")
    public ResponseEntity<EmpresaDTO> buscarPorCif(@PathVariable String cif) {
        logger.info("GET /api/empresas/cif/{} - Buscar por CIF", cif);
        
        return empresaService.obtenerPorCif(cif)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Buscar por sector
     * GET /api/empresas/sector/{sector}
     */
    @GetMapping("/sector/{sector}")
    public ResponseEntity<List<EmpresaDTO>> buscarPorSector(@PathVariable String sector) {
        logger.info("GET /api/empresas/sector/{} - Buscar por sector", sector);
        List<EmpresaDTO> empresas = empresaService.buscarPorSector(sector);
        return ResponseEntity.ok(empresas);
    }

    /**
     * Buscar por razón social
     * GET /api/empresas/buscar?texto=...
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<EmpresaDTO>> buscarPorRazonSocial(
            @RequestParam String texto) {
        
        logger.info("GET /api/empresas/buscar?texto={}", texto);
        List<EmpresaDTO> empresas = empresaService.buscarPorRazonSocial(texto);
        return ResponseEntity.ok(empresas);
    }

    /**
     * Desactivar empresa (soft delete)
     * PATCH /api/empresas/{id}/desactivar
     */
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        logger.info("PATCH /api/empresas/{}/desactivar", id);
        
        try {
            empresaService.desactivar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error al desactivar empresa: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Activar empresa
     * PATCH /api/empresas/{id}/activar
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        logger.info("PATCH /api/empresas/{}/activar", id);
        
        try {
            empresaService.activar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error al activar empresa: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener estadísticas
     * GET /api/empresas/estadisticas/activas
     */
    @GetMapping("/estadisticas/activas")
    public ResponseEntity<Long> contarActivas() {
        logger.info("GET /api/empresas/estadisticas/activas");
        long count = empresaService.contarEmpresasActivas();
        return ResponseEntity.ok(count);
    }
}

