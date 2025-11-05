package com.empresa.gestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot
 * 
 * Esta aplicación demuestra:
 * - Spring Boot 3.3 con Java 21
 * - Arquitectura en capas (Controller -> Service -> Repository)
 * - JPA con H2 (base de datos en memoria)
 * - Separación de DTOs y Entities
 * - Mappers manuales
 */
@SpringBootApplication
public class GestionEmpresasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionEmpresasApplication.class, args);
        System.out.println("\n=================================================");
        System.out.println("✓ Aplicación iniciada correctamente");
        System.out.println("✓ Puerto: 8080");
        System.out.println("✓ Consola H2: http://localhost:8080/h2-console");
        System.out.println("✓ API Base: http://localhost:8080/api");
        System.out.println("=================================================\n");
    }

}


