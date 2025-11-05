package com.empresa.gestion.repository;

import com.empresa.gestion.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Sede
 * 
 * SPRING DATA JPA:
 * - Métodos CRUD automáticos heredados de JpaRepository
 * - Consultas personalizadas por nombre de método
 * - Consultas JPQL con @Query
 */
@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {

    /**
     * Buscar todas las sedes de una empresa específica
     */
    List<Sede> findByEmpresaId(Long empresaId);

    /**
     * Buscar sedes por ciudad
     */
    List<Sede> findByCiudadIgnoreCase(String ciudad);

    /**
     * Buscar sedes por provincia
     */
    List<Sede> findByProvinciaIgnoreCase(String provincia);

    /**
     * Buscar la sede principal de una empresa
     */
    Optional<Sede> findByEmpresaIdAndEsPrincipalTrue(Long empresaId);

    /**
     * Buscar sedes por ciudad y empresa
     */
    List<Sede> findByEmpresaIdAndCiudadIgnoreCase(Long empresaId, String ciudad);

    /**
     * Contar sedes de una empresa
     */
    long countByEmpresaId(Long empresaId);

    /**
     * Buscar sedes con capacidad de almacenamiento mayor a un valor
     */
    List<Sede> findByCapacidadAlmacenamientoGreaterThanEqual(Double capacidad);

    /**
     * Buscar sedes por nombre (búsqueda parcial)
     */
    @Query("SELECT s FROM Sede s WHERE LOWER(s.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Sede> buscarPorNombre(@Param("texto") String texto);

    /**
     * Verificar si existe una sede principal para una empresa
     */
    boolean existsByEmpresaIdAndEsPrincipalTrue(Long empresaId);

    /**
     * Buscar todas las sedes de una empresa con su información completa
     * Ejemplo de JOIN FETCH para evitar N+1 queries
     */
    @Query("SELECT s FROM Sede s JOIN FETCH s.empresa WHERE s.empresa.id = :empresaId")
    List<Sede> findSedesConEmpresaByEmpresaId(@Param("empresaId") Long empresaId);
}

