package com.empresa.gestion.repository;

import com.empresa.gestion.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Empresa
 * 
 * SPRING DATA JPA + JAVA 8:
 * - Proporciona implementación automática de métodos CRUD
 * - Soporta consultas derivadas de nombres de métodos
 * - Permite consultas personalizadas con @Query
 * - Optional para manejar valores nulos de forma segura
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    /**
     * Buscar empresa por CIF (consulta derivada del nombre del método)
     * Spring Data JPA genera automáticamente la implementación
     */
    Optional<Empresa> findByCif(String cif);

    /**
     * Buscar empresas activas
     */
    List<Empresa> findByActivoTrue();

    /**
     * Buscar empresas por sector
     */
    List<Empresa> findBySectorIgnoreCase(String sector);

    /**
     * Buscar empresas por razón social (búsqueda parcial)
     * LIKE es case-sensitive por defecto en H2
     */
    @Query("SELECT e FROM Empresa e WHERE LOWER(e.razonSocial) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Empresa> buscarPorRazonSocial(@Param("texto") String texto);

    /**
     * Verificar si existe una empresa con un CIF específico
     */
    boolean existsByCif(String cif);

    /**
     * Buscar empresas con facturación mayor a un valor
     */
    List<Empresa> findByFacturacionAnualGreaterThan(Double facturacion);

    /**
     * Contar empresas activas
     */
    long countByActivoTrue();

    /**
     * Buscar empresas por sector y activas
     * Ejemplo de consulta con múltiples condiciones
     */
    List<Empresa> findBySectorAndActivoTrue(String sector);
}

