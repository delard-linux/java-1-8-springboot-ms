package com.empresa.gestion.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para transferir datos de Empresa entre capas
 * 
 * JAVA 8 + SPRING BOOT 2.7:
 * - Clase tradicional con getters/setters
 * - javax.validation.constraints.* (Bean Validation estándar)
 */
public class EmpresaDTO {

    private Long id;

    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 200, message = "La razón social no puede superar 200 caracteres")
    private String razonSocial;

    @NotBlank(message = "El CIF es obligatorio")
    @Size(max = 20, message = "El CIF no puede superar 20 caracteres")
    private String cif;

    @Email(message = "El email debe ser válido")
    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String telefono;

    @Size(max = 100)
    private String sector;

    private LocalDate fechaAlta;

    private Boolean activo;

    private Double facturacionAnual;

    private Integer numeroEmpleados;

    private List<SedeDTO> sedes = new ArrayList<>();

    // Constructores
    public EmpresaDTO() {
    }

    public EmpresaDTO(Long id, String razonSocial, String cif) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.cif = cif;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Double getFacturacionAnual() {
        return facturacionAnual;
    }

    public void setFacturacionAnual(Double facturacionAnual) {
        this.facturacionAnual = facturacionAnual;
    }

    public Integer getNumeroEmpleados() {
        return numeroEmpleados;
    }

    public void setNumeroEmpleados(Integer numeroEmpleados) {
        this.numeroEmpleados = numeroEmpleados;
    }

    public List<SedeDTO> getSedes() {
        return sedes;
    }

    public void setSedes(List<SedeDTO> sedes) {
        this.sedes = sedes;
    }
}


