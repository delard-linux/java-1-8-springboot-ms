# Gestión de Empresas - Microservicio CRUD

## 📋 Descripción

Microservicio CRUD desarrollado con **Spring Boot 2.7** y **Java 8** para la gestión de empresas y sus sedes físicas.

**Proyecto Legacy** que simula un entorno de desarrollo antiguo típico de 2018-2020.

El proyecto demuestra:
-✅ Arquitectura en capas (no hexagonal)
-✅ Base de datos H2 en memoria
-✅ Relación 1-N entre Empresa y Sede
-✅ Separación clara: Entities, DTOs, Mappers
-✅ Operaciones CRUD completas
-✅ Tecnologías legacy: Java 8, Spring Boot 2.7, javax.*

---

## 🏗️ Arquitectura

```text
┌─────────────────┐
│   Controller    │  <- Capa REST (API endpoints)
├─────────────────┤
│    Service      │  <- Lógica de negocio
├─────────────────┤
│   Repository    │  <- Acceso a datos (Spring Data JPA)
├─────────────────┤
│     Entity      │  <- Entidades JPA
└─────────────────┘
        ↕
    H2 Database (memoria)
```

### Estructura de paquetes

```text
com.empresa.gestion/
├── controller/       # Controladores REST
├── service/          # Lógica de negocio
├── repository/       # Repositorios JPA
├── entity/           # Entidades de base de datos
├── dto/              # Data Transfer Objects
└── mapper/           # Conversión Entity ↔ DTO
```

---

## 🚀 Tecnologías

| Tecnología | Versión |
|------------|---------|
| Java | 1.8 |
| Spring Boot | 2.7.18 |
| Spring Data JPA | 2.7.18 |
| H2 Database | runtime |
| Maven | 3.x |

---

## ⚙️ Requisitos

- **JDK 8** instalado en `/opt/java/jdk-8`
- **Maven 3.x** (incluido Maven Wrapper en el proyecto)

### Configuración de VS Code

Para evitar warnings de compatibilidad, crea o edita `.vscode/settings.json`:

```json
{
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-1.8",
            "path": "/opt/java/jdk-8",
            "default": true
        }
    ]
}
```

Ajusta la ruta según tu instalación de Java 8.

---

## 🏃 Cómo ejecutar

### 1. Clonar/descargar el proyecto

```bash
cd java-1-8-springboot-ms
```

### 2. Compilar

```bash
mvn clean install
```

### 3. Ejecutar

```bash
mvn spring-boot:run
```

La aplicación se iniciará en **<http://localhost:8080>**

---

## 🗄️ Base de Datos H2

### Consola H2

Accede a la consola de H2 en: **<http://localhost:8080/h2-console>**

**Configuración de conexión:**
-**JDBC URL:** `jdbc:h2:mem:empresasdb`
-**Usuario:** `sa`
-**Contraseña:** *(vacío)*

### Datos precargados

El sistema incluye **5 empresas** y **12 sedes** de ejemplo (ver `src/main/resources/data.sql`)

---

## 📡 API Endpoints

### Empresas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/empresas` | Listar todas las empresas |
| GET | `/api/empresas/{id}` | Obtener empresa por ID |
| GET | `/api/empresas/activas` | Listar empresas activas |
| GET | `/api/empresas/cif/{cif}` | Buscar por CIF |
| GET | `/api/empresas/sector/{sector}` | Buscar por sector |
| GET | `/api/empresas/buscar?texto=...` | Buscar por razón social |
| POST | `/api/empresas` | Crear nueva empresa |
| PUT | `/api/empresas/{id}` | Actualizar empresa |
| DELETE | `/api/empresas/{id}` | Eliminar empresa |
| PATCH | `/api/empresas/{id}/activar` | Activar empresa |
| PATCH | `/api/empresas/{id}/desactivar` | Desactivar empresa |
| GET | `/api/empresas/estadisticas/activas` | Contar empresas activas |

### Sedes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/sedes` | Listar todas las sedes |
| GET | `/api/sedes/{id}` | Obtener sede por ID |
| GET | `/api/sedes/empresa/{empresaId}` | Sedes de una empresa |
| GET | `/api/sedes/empresa/{empresaId}/principal` | Sede principal de empresa |
| GET | `/api/sedes/ciudad/{ciudad}` | Buscar por ciudad |
| GET | `/api/sedes/provincia/{provincia}` | Buscar por provincia |
| GET | `/api/sedes/buscar?texto=...` | Buscar por nombre |
| POST | `/api/sedes` | Crear nueva sede |
| PUT | `/api/sedes/{id}` | Actualizar sede |
| DELETE | `/api/sedes/{id}` | Eliminar sede |
| GET | `/api/sedes/empresa/{empresaId}/count` | Contar sedes de empresa |

---

## 🧪 Ejemplos de uso (cURL)

### Listar todas las empresas

```bash
curl http://localhost:8080/api/empresas
```

### Obtener empresa por ID

```bash
curl http://localhost:8080/api/empresas/1
```

### Crear nueva empresa

```bash
curl -X POST http://localhost:8080/api/empresas \
  -H "Content-Type: application/json" \
  -d '{
    "razonSocial": "Innovación Digital S.L.",
    "cif": "B99887766",
    "email": "info@innovaciondigital.es",
    "telefono": "911222333",
    "sector": "Tecnología",
    "activo": true,
    "facturacionAnual": 2000000,
    "numeroEmpleados": 25
  }'
```

### Obtener sedes de una empresa

```bash
curl http://localhost:8080/api/sedes/empresa/1
```

### Crear nueva sede

```bash
curl -X POST http://localhost:8080/api/sedes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Delegación Málaga",
    "direccion": "Calle Larios, 10",
    "ciudad": "Málaga",
    "provincia": "Málaga",
    "codigoPostal": "29015",
    "pais": "España",
    "telefono": "952123456",
    "email": "malaga@techsolutions.es",
    "esPrincipal": false,
    "capacidadAlmacenamiento": 250.0,
    "horarioRecepcion": "L-V: 9:00-18:00",
    "empresaId": 1
  }'
```

---

## 📚 Modelo de Datos

### Empresa

```json
{
  "id": 1,
  "razonSocial": "Tech Solutions S.L.",
  "cif": "B12345678",
  "email": "info@techsolutions.es",
  "telefono": "912345678",
  "sector": "Tecnología",
  "fechaAlta": "2020-01-15",
  "activo": true,
  "facturacionAnual": 5000000.00,
  "numeroEmpleados": 50,
  "sedes": [...]
}
```

### Sede

```json
{
  "id": 1,
  "nombre": "Sede Central Madrid",
  "direccion": "Calle Gran Vía, 45",
  "ciudad": "Madrid",
  "provincia": "Madrid",
  "codigoPostal": "28013",
  "pais": "España",
  "telefono": "912345678",
  "email": "madrid@techsolutions.es",
  "esPrincipal": true,
  "capacidadAlmacenamiento": 500.0,
  "horarioRecepcion": "L-V: 8:00-18:00",
  "empresaId": 1
}
```

---

## �️ Proyecto Legacy

Este proyecto simula un **entorno de desarrollo antiguo** (2018-2020) con:

- ☕ **Java 8** (JDK 1.8)
- 🍃 **Spring Boot 2.7.18**
- 📦 **javax.*** packages (pre-Jakarta EE)
- � **Maven 3.6.3**
- 💾 **H2 Database** en memoria

**Propósito**: Practicar mantenimiento y modernización de aplicaciones legacy.

---

## 📂 Estructura del proyecto

```text
java-1-8-springboot-ms/
├── src/
│   ├── main/
│   │   ├── java/com/empresa/gestion/
│   │   │   ├── controller/
│   │   │   │   ├── EmpresaController.java
│   │   │   │   └── SedeController.java
│   │   │   ├── service/
│   │   │   │   ├── EmpresaService.java
│   │   │   │   └── SedeService.java
│   │   │   ├── repository/
│   │   │   │   ├── EmpresaRepository.java
│   │   │   │   └── SedeRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── Empresa.java
│   │   │   │   └── Sede.java
│   │   │   ├── dto/
│   │   │   │   ├── EmpresaDTO.java
│   │   │   │   └── SedeDTO.java
│   │   │   ├── mapper/
│   │   │   │   ├── EmpresaMapper.java
│   │   │   │   └── SedeMapper.java
│   │   │   └── GestionEmpresasApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/ (para tests)
├── pom.xml
├── README.md
├── MIGRACION_JAVA_25.md
├── EJEMPLOS_JAVA_25.md
└── .gitignore
```

---

## 🎯 Características destacadas

### 1. Separación de capas

-**Controller**: Solo gestiona HTTP
-**Service**: Lógica de negocio y validaciones
-**Repository**: Solo acceso a datos
-**Entity vs DTO**: Separación clara de responsabilidades

### 2. Validaciones

-Validación con anotaciones (`@Valid`, `@NotBlank`, etc.)
-Validaciones de negocio en la capa Service
-Manejo de excepciones con `ResponseEntity`

### 3. Relación 1-N bien gestionada

- `@OneToMany` y `@ManyToOne` correctamente configurados
- Métodos helper para gestionar relación bidireccional
- Prevención de recursión infinita en mappers

### 4. Buenas prácticas

-Uso de `Optional` para manejar nulos
-Logging con SLF4J
-Transacciones con `@Transactional`
-Inyección de dependencias por constructor
-Nombres descriptivos en consultas

---

## 📝 Notas importantes

### Entorno Legacy

Este proyecto simula un **entorno antiguo con Java 8** y Spring Boot 2.7.

Características del código legacy:
-Clases tradicionales con getters/setters (no Records)
-Sin inferencia de tipos con `var`
-Switch tradicional (no expressions)
-javax.* packages (pre-Jakarta EE)

### Para producción

Este es un proyecto **educativo**. Para producción considera:
-✅ Base de datos persistente (PostgreSQL, MySQL, etc.)
-✅ Seguridad (Spring Security)
-✅ Tests unitarios e integración
-✅ Documentación API (Swagger/OpenAPI)
-✅ Manejo centralizado de excepciones
-✅ Paginación en endpoints de listado
-✅ DTOs específicos para request/response
-✅ Versionado de API
-✅ CORS configurado adecuadamente

---

## 👤 Autor

Proyecto educativo para demostrar:

- Desarrollo con Spring Boot 2.7 + Java 8
- Arquitectura en capas
- Mantenimiento de código legacy

---

## 📄 Licencia

Proyecto de ejemplo - Uso educativo

---

## 🔗 Enlaces útiles

- [Spring Boot 2.7 Documentation](https://docs.spring.io/spring-boot/docs/2.7.x/reference/html/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](https://www.h2database.com/)
- [Java 8 Documentation](https://docs.oracle.com/javase/8/docs/)
