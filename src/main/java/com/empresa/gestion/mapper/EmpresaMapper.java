package com.empresa.gestion.mapper;

import com.empresa.gestion.dto.EmpresaDTO;
import com.empresa.gestion.entity.Empresa;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper manual para convertir entre Entity y DTO de Empresa
 * 
 * JAVA 21 + SPRING BOOT 3.3:
 * - Mapper manual usando streams modernos
 * - Sin librerías adicionales (MapStruct, ModelMapper)
 */
@Component
public class EmpresaMapper {

    private final SedeMapper sedeMapper;

    public EmpresaMapper(SedeMapper sedeMapper) {
        this.sedeMapper = sedeMapper;
    }

    /**
     * Convierte Entity a DTO
     * 
     * JAVA 21:
     * Puede usar 'var' para inferencia de tipos o tipos explícitos
     * var sedesDTO = entity.getSedes()...
     */
    public EmpresaDTO toDTO(Empresa entity) {
        if (entity == null) {
            return null;
        }

        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(entity.getId());
        dto.setRazonSocial(entity.getRazonSocial());
        dto.setCif(entity.getCif());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setSector(entity.getSector());
        dto.setFechaAlta(entity.getFechaAlta());
        dto.setActivo(entity.getActivo());
        dto.setFacturacionAnual(entity.getFacturacionAnual());
        dto.setNumeroEmpleados(entity.getNumeroEmpleados());

        // Convertir sedes
        if (entity.getSedes() != null && !entity.getSedes().isEmpty()) {
            dto.setSedes(entity.getSedes().stream()
                    .map(sedeMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    /**
     * Convierte DTO a Entity (para crear nueva entidad)
     */
    public Empresa toEntity(EmpresaDTO dto) {
        if (dto == null) {
            return null;
        }

        Empresa entity = new Empresa();
        entity.setRazonSocial(dto.getRazonSocial());
        entity.setCif(dto.getCif());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        entity.setSector(dto.getSector());
        entity.setFechaAlta(dto.getFechaAlta());
        entity.setActivo(dto.getActivo());
        entity.setFacturacionAnual(dto.getFacturacionAnual());
        entity.setNumeroEmpleados(dto.getNumeroEmpleados());

        return entity;
    }

    /**
     * Actualiza una entidad existente con datos del DTO
     * (útil para operaciones de UPDATE)
     */
    public void updateEntityFromDTO(EmpresaDTO dto, Empresa entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setRazonSocial(dto.getRazonSocial());
        entity.setCif(dto.getCif());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        entity.setSector(dto.getSector());
        entity.setActivo(dto.getActivo());
        entity.setFacturacionAnual(dto.getFacturacionAnual());
        entity.setNumeroEmpleados(dto.getNumeroEmpleados());
    }

    /**
     * Convierte una lista de entities a DTOs usando streams
     */
    public List<EmpresaDTO> toDTOList(List<Empresa> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}


