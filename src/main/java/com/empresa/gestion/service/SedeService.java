package com.empresa.gestion.service;

import com.empresa.gestion.dto.SedeDTO;
import com.empresa.gestion.entity.Empresa;
import com.empresa.gestion.entity.Sede;
import com.empresa.gestion.mapper.SedeMapper;
import com.empresa.gestion.repository.EmpresaRepository;
import com.empresa.gestion.repository.SedeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de Sedes
 * 
 * ARQUITECTURA EN CAPAS:
 * - Contiene lógica de negocio relacionada con sedes
 * - Gestiona la relación con empresas
 * - Valida reglas de negocio
 */
@Service
@Transactional
public class SedeService {

    private static final Logger logger = LoggerFactory.getLogger(SedeService.class);

    private final SedeRepository sedeRepository;
    private final EmpresaRepository empresaRepository;
    private final SedeMapper sedeMapper;

    public SedeService(SedeRepository sedeRepository, 
                       EmpresaRepository empresaRepository,
                       SedeMapper sedeMapper) {
        this.sedeRepository = sedeRepository;
        this.empresaRepository = empresaRepository;
        this.sedeMapper = sedeMapper;
    }

    /**
     * Obtener todas las sedes
     */
    @Transactional(readOnly = true)
    public List<SedeDTO> obtenerTodas() {
        logger.debug("Obteniendo todas las sedes");
        List<Sede> sedes = sedeRepository.findAll();
        return sedeMapper.toDTOList(sedes);
    }

    /**
     * Obtener sede por ID
     */
    @Transactional(readOnly = true)
    public Optional<SedeDTO> obtenerPorId(Long id) {
        logger.debug("Buscando sede con ID: {}", id);
        return sedeRepository.findById(id)
                .map(sedeMapper::toDTO);
    }

    /**
     * Obtener todas las sedes de una empresa
     */
    @Transactional(readOnly = true)
    public List<SedeDTO> obtenerPorEmpresa(Long empresaId) {
        logger.debug("Obteniendo sedes de la empresa: {}", empresaId);
        List<Sede> sedes = sedeRepository.findByEmpresaId(empresaId);
        return sedeMapper.toDTOList(sedes);
    }

    /**
     * Crear una nueva sede
     * 
     * Validaciones:
     * - La empresa debe existir
     * - Si es sede principal, no debe haber otra sede principal para esa empresa
     */
    public SedeDTO crear(SedeDTO sedeDTO) {
        logger.info("Creando nueva sede: {}", sedeDTO.getNombre());

        // Validar que la empresa existe
        Empresa empresa = empresaRepository.findById(sedeDTO.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Empresa no encontrada con ID: " + sedeDTO.getEmpresaId()));

        // Si es sede principal, verificar que no exista otra
        if (Boolean.TRUE.equals(sedeDTO.getEsPrincipal()) 
                && sedeRepository.existsByEmpresaIdAndEsPrincipalTrue(sedeDTO.getEmpresaId())) {
            throw new IllegalArgumentException(
                    "Ya existe una sede principal para esta empresa");
        }

        Sede sede = sedeMapper.toEntity(sedeDTO);
        sede.setEmpresa(empresa);
        
        Sede sedeGuardada = sedeRepository.save(sede);
        logger.info("Sede creada con ID: {}", sedeGuardada.getId());
        
        return sedeMapper.toDTO(sedeGuardada);
    }

    /**
     * Actualizar una sede existente
     */
    public SedeDTO actualizar(Long id, SedeDTO sedeDTO) {
        logger.info("Actualizando sede con ID: {}", id);

        Sede sedeExistente = sedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada con ID: " + id));

        // Si se cambia a sede principal, verificar que no exista otra
        if (Boolean.TRUE.equals(sedeDTO.getEsPrincipal()) 
                && !Boolean.TRUE.equals(sedeExistente.getEsPrincipal())) {
            
            if (sedeRepository.existsByEmpresaIdAndEsPrincipalTrue(sedeExistente.getEmpresa().getId())) {
                throw new IllegalArgumentException(
                        "Ya existe una sede principal para esta empresa");
            }
        }

        sedeMapper.updateEntityFromDTO(sedeDTO, sedeExistente);
        Sede sedeActualizada = sedeRepository.save(sedeExistente);
        
        logger.info("Sede actualizada: {}", sedeActualizada.getId());
        return sedeMapper.toDTO(sedeActualizada);
    }

    /**
     * Eliminar una sede
     */
    public void eliminar(Long id) {
        logger.info("Eliminando sede con ID: {}", id);

        if (!sedeRepository.existsById(id)) {
            throw new IllegalArgumentException("Sede no encontrada con ID: " + id);
        }

        sedeRepository.deleteById(id);
        logger.info("Sede eliminada: {}", id);
    }

    /**
     * Buscar sedes por ciudad
     */
    @Transactional(readOnly = true)
    public List<SedeDTO> buscarPorCiudad(String ciudad) {
        logger.debug("Buscando sedes en la ciudad: {}", ciudad);
        List<Sede> sedes = sedeRepository.findByCiudadIgnoreCase(ciudad);
        return sedeMapper.toDTOList(sedes);
    }

    /**
     * Buscar sedes por provincia
     */
    @Transactional(readOnly = true)
    public List<SedeDTO> buscarPorProvincia(String provincia) {
        logger.debug("Buscando sedes en la provincia: {}", provincia);
        List<Sede> sedes = sedeRepository.findByProvinciaIgnoreCase(provincia);
        return sedeMapper.toDTOList(sedes);
    }

    /**
     * Obtener la sede principal de una empresa
     */
    @Transactional(readOnly = true)
    public Optional<SedeDTO> obtenerSedePrincipal(Long empresaId) {
        logger.debug("Buscando sede principal de la empresa: {}", empresaId);
        return sedeRepository.findByEmpresaIdAndEsPrincipalTrue(empresaId)
                .map(sedeMapper::toDTO);
    }

    /**
     * Buscar sedes por nombre (búsqueda parcial)
     */
    @Transactional(readOnly = true)
    public List<SedeDTO> buscarPorNombre(String texto) {
        logger.debug("Buscando sedes por nombre: {}", texto);
        List<Sede> sedes = sedeRepository.buscarPorNombre(texto);
        return sedeMapper.toDTOList(sedes);
    }

    /**
     * Contar sedes de una empresa
     */
    @Transactional(readOnly = true)
    public long contarSedesPorEmpresa(Long empresaId) {
        return sedeRepository.countByEmpresaId(empresaId);
    }
}

