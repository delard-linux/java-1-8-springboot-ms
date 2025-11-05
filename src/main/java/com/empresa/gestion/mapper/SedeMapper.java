package com.empresa.gestion.mapper;

import com.empresa.gestion.dto.SedeDTO;
import com.empresa.gestion.entity.Sede;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper manual para convertir entre Entity y DTO de Sede
 * 
 * JAVA 21 + SPRING BOOT 3.3:
 * - Sintaxis moderna de Java 21
 */
@Component
public class SedeMapper {

    /**
     * Convierte Entity a DTO (sin incluir empresa completa para evitar recursión)
     */
    public SedeDTO toDTO(Sede entity) {
        if (entity == null) {
            return null;
        }

        SedeDTO dto = new SedeDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setCiudad(entity.getCiudad());
        dto.setProvincia(entity.getProvincia());
        dto.setCodigoPostal(entity.getCodigoPostal());
        dto.setPais(entity.getPais());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        dto.setEsPrincipal(entity.getEsPrincipal());
        dto.setCapacidadAlmacenamiento(entity.getCapacidadAlmacenamiento());
        dto.setHorarioRecepcion(entity.getHorarioRecepcion());

        // Solo incluir el ID de la empresa para evitar recursión infinita
        if (entity.getEmpresa() != null) {
            dto.setEmpresaId(entity.getEmpresa().getId());
        }

        return dto;
    }

    /**
     * Convierte DTO a Entity (sin establecer la relación con Empresa)
     */
    public Sede toEntity(SedeDTO dto) {
        if (dto == null) {
            return null;
        }

        Sede entity = new Sede();
        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setCiudad(dto.getCiudad());
        entity.setProvincia(dto.getProvincia());
        entity.setCodigoPostal(dto.getCodigoPostal());
        entity.setPais(dto.getPais());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        entity.setEsPrincipal(dto.getEsPrincipal());
        entity.setCapacidadAlmacenamiento(dto.getCapacidadAlmacenamiento());
        entity.setHorarioRecepcion(dto.getHorarioRecepcion());

        return entity;
    }

    /**
     * Actualiza una entidad existente con datos del DTO
     */
    public void updateEntityFromDTO(SedeDTO dto, Sede entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setCiudad(dto.getCiudad());
        entity.setProvincia(dto.getProvincia());
        entity.setCodigoPostal(dto.getCodigoPostal());
        entity.setPais(dto.getPais());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        entity.setEsPrincipal(dto.getEsPrincipal());
        entity.setCapacidadAlmacenamiento(dto.getCapacidadAlmacenamiento());
        entity.setHorarioRecepcion(dto.getHorarioRecepcion());
    }

    /**
     * Convierte lista de entities a DTOs
     */
    public List<SedeDTO> toDTOList(List<Sede> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

