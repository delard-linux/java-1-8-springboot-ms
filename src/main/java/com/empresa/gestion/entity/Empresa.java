package com.empresa.gestion.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que representa una Empresa
 * 
 * JAVA 8 + SPRING BOOT 2.7:
 * - Usamos clase tradicional con getters/setters
 * - javax.persistence.* (JPA estándar)
 * - Compatible con Java 8
 */
@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razon_social", nullable = false, length = 200)
    private String razonSocial;

    @Column(name = "cif", nullable = false, unique = true, length = 20)
    private String cif;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "sector", length = 100)
    private String sector;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "facturacion_anual")
    private Double facturacionAnual;

    @Column(name = "numero_empleados")
    private Integer numeroEmpleados;

    // Relación 1-N: Una empresa puede tener múltiples sedes
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Sede> sedes = new ArrayList<>();

    // Constructores
    public Empresa() {
        this.activo = true;
        this.fechaAlta = LocalDate.now();
    }

    public Empresa(String razonSocial, String cif) {
        this();
        this.razonSocial = razonSocial;
        this.cif = cif;
    }

    // Métodos helper para gestionar la relación bidireccional
    public void addSede(Sede sede) {
        sedes.add(sede);
        sede.setEmpresa(this);
    }

    public void removeSede(Sede sede) {
        sedes.remove(sede);
        sede.setEmpresa(null);
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

    public List<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(List<Sede> sedes) {
        this.sedes = sedes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id) && Objects.equals(cif, empresa.cif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cif);
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", razonSocial='" + razonSocial + '\'' +
                ", cif='" + cif + '\'' +
                ", activo=" + activo +
                '}';
    }
}


