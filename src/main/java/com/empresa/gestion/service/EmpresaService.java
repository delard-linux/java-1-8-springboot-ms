package com.empresa.gestion.service;

import com.empresa.gestion.dto.EmpresaDTO;
import com.empresa.gestion.entity.Empresa;
import com.empresa.gestion.mapper.EmpresaMapper;
import com.empresa.gestion.repository.EmpresaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de Empresas
 * 
 * ARQUITECTURA EN CAPAS:
 * - Esta capa contiene la lógica de negocio
 * - Se comunica con la capa de repositorio (datos)
 * - Es utilizada por la capa de controladores
 * 
 * JAVA 8 + SPRING BOOT 2.7:
 * - Optional para manejar nulos de forma segura
 * - Transacciones con @Transactional
 */
@Service
@Transactional
public class EmpresaService {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaService.class);

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    public EmpresaService(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    /**
     * Obtener todas las empresas
     */
    @Transactional(readOnly = true)
    public List<EmpresaDTO> obtenerTodas() {
        logger.debug("Obteniendo todas las empresas");
        List<Empresa> empresas = empresaRepository.findAll();
        return empresaMapper.toDTOList(empresas);
    }

    /**
     * Obtener empresa por ID
     */
    @Transactional(readOnly = true)
    public Optional<EmpresaDTO> obtenerPorId(Long id) {
        logger.debug("Buscando empresa con ID: {}", id);
        return empresaRepository.findById(id)
                .map(empresaMapper::toDTO);
    }

    /**
     * Obtener empresa por CIF
     */
    @Transactional(readOnly = true)
    public Optional<EmpresaDTO> obtenerPorCif(String cif) {
        logger.debug("Buscando empresa con CIF: {}", cif);
        return empresaRepository.findByCif(cif)
                .map(empresaMapper::toDTO);
    }

    /**
     * Crear una nueva empresa
     * 
     * Validaciones de negocio:
     * - El CIF no debe existir previamente
     */
    public EmpresaDTO crear(EmpresaDTO empresaDTO) {
        logger.info("Creando nueva empresa: {}", empresaDTO.getRazonSocial());

        // Validación: CIF único
        if (empresaRepository.existsByCif(empresaDTO.getCif())) {
            throw new IllegalArgumentException("Ya existe una empresa con el CIF: " + empresaDTO.getCif());
        }

        Empresa empresa = empresaMapper.toEntity(empresaDTO);
        Empresa empresaGuardada = empresaRepository.save(empresa);
        
        logger.info("Empresa creada con ID: {}", empresaGuardada.getId());
        return empresaMapper.toDTO(empresaGuardada);
    }

    /**
     * Actualizar una empresa existente
     */
    public EmpresaDTO actualizar(Long id, EmpresaDTO empresaDTO) {
        logger.info("Actualizando empresa con ID: {}", id);

        Empresa empresaExistente = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con ID: " + id));

        // Validar CIF único si se ha modificado
        if (!empresaExistente.getCif().equals(empresaDTO.getCif()) 
                && empresaRepository.existsByCif(empresaDTO.getCif())) {
            throw new IllegalArgumentException("Ya existe una empresa con el CIF: " + empresaDTO.getCif());
        }

        empresaMapper.updateEntityFromDTO(empresaDTO, empresaExistente);
        Empresa empresaActualizada = empresaRepository.save(empresaExistente);
        
        logger.info("Empresa actualizada: {}", empresaActualizada.getId());
        return empresaMapper.toDTO(empresaActualizada);
    }

    /**
     * Eliminar una empresa
     */
    public void eliminar(Long id) {
        logger.info("Eliminando empresa con ID: {}", id);

        if (!empresaRepository.existsById(id)) {
            throw new IllegalArgumentException("Empresa no encontrada con ID: " + id);
        }

        empresaRepository.deleteById(id);
        logger.info("Empresa eliminada: {}", id);
    }

    /**
     * Buscar empresas activas
     */
    @Transactional(readOnly = true)
    public List<EmpresaDTO> obtenerActivas() {
        logger.debug("Obteniendo empresas activas");
        List<Empresa> empresas = empresaRepository.findByActivoTrue();
        return empresaMapper.toDTOList(empresas);
    }

    /**
     * Buscar empresas por sector
     */
    @Transactional(readOnly = true)
    public List<EmpresaDTO> buscarPorSector(String sector) {
        logger.debug("Buscando empresas del sector: {}", sector);
        List<Empresa> empresas = empresaRepository.findBySectorIgnoreCase(sector);
        return empresaMapper.toDTOList(empresas);
    }

    /**
     * Buscar empresas por razón social (búsqueda parcial)
     */
    @Transactional(readOnly = true)
    public List<EmpresaDTO> buscarPorRazonSocial(String texto) {
        logger.debug("Buscando empresas por razón social: {}", texto);
        List<Empresa> empresas = empresaRepository.buscarPorRazonSocial(texto);
        return empresaMapper.toDTOList(empresas);
    }

    /**
     * Desactivar una empresa (soft delete)
     */
    public void desactivar(Long id) {
        logger.info("Desactivando empresa con ID: {}", id);

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con ID: " + id));

        empresa.setActivo(false);
        empresaRepository.save(empresa);
        
        logger.info("Empresa desactivada: {}", id);
    }

    /**
     * Activar una empresa
     */
    public void activar(Long id) {
        logger.info("Activando empresa con ID: {}", id);

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con ID: " + id));

        empresa.setActivo(true);
        empresaRepository.save(empresa);
        
        logger.info("Empresa activada: {}", id);
    }

    /**
     * Obtener estadísticas
     */
    @Transactional(readOnly = true)
    public long contarEmpresasActivas() {
        return empresaRepository.countByActivoTrue();
    }
}

