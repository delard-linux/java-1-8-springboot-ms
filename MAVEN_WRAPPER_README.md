# Maven Wrapper + Java 8

## 📦 Maven Wrapper Instalado

Se ha configurado **Maven Wrapper 3.6.3** compatible con Java 8 que carga automáticamente las variables de entorno desde el archivo `.env`.

### Archivos del Wrapper

```text
proyecto/
├── .env              # Variables de entorno (carga automática)
├── mvnw              # Script para Linux/Mac (modificado)
├── mvnw.cmd          # Script para Windows
└── .mvn/
    └── wrapper/
        ├── maven-wrapper.jar        # JAR del wrapper
        └── maven-wrapper.properties # Configuración
```

## ⚙️ Configuración Automática con `.env`

### Archivo `.env`

El archivo `.env` contiene la configuración del JDK 8:

```bash
JAVA_PRJ_HOME=/opt/java/jdk-8
```

### 🎯 Carga Automática

El script `mvnw` **carga automáticamente** el archivo `.env` al ejecutarse. No necesitas hacer `source .env` ni exportar variables manualmente.

Cuando ejecutas `./mvnw`, automáticamente:
1.✅ Lee el archivo `.env`
2.✅ Configura `JAVA_HOME=$JAVA_PRJ_HOME` (según la ruta en `.env`)
3.✅ Muestra un mensaje de confirmación
4.✅ Ejecuta Maven con el Java correcto

### ⚙️ Configurar tu JDK

**IMPORTANTE**: Edita el archivo `.env` y ajusta la ruta `JAVA_PRJ_HOME` a tu instalación de Java:

```bash
# Editar .env
nano .env  # o vim .env

# Cambiar JAVA_PRJ_HOME=/opt/java/jdk-8
# Por tu ruta real, por ejemplo:
JAVA_PRJ_HOME=/opt/java/jdk-8
```

**Para encontrar tu instalación de Java:**

```bash
# Ver Java disponible
ls /usr/lib/jvm/

# Ver JAVA_HOME actual del sistema
echo $JAVA_HOME

# Ver ubicación de java
which java
readlink -f $(which java)
```

## 📝 Uso del Maven Wrapper

**¡Simplemente usa `./mvnw`!** El script carga automáticamente el Java 8 desde `.env`.

### Compilar el Proyecto

```bash
./mvnw clean compile
```

### Ejecutar Tests

```bash
./mvnw test
```

### Empaquetar (JAR)

```bash
./mvnw clean package
```

### Ejecutar la Aplicación

```bash
./mvnw spring-boot:run
```

### Limpiar el Proyecto

```bash
./mvnw clean
```

### Ver Versión de Maven y Java

```bash
./mvnw -version
```

## ℹ️ Sobre el Proyecto

Este proyecto simula un **entorno legacy** con:
-**Java 8** (JDK 1.8)
-**Spring Boot 2.7.18**
-**javax.*** imports (antes de Jakarta EE)
-**Maven 3.6.3**

Ideal para practicar mantenimiento de proyectos antiguos.

## ⚠️ Notas Importantes

### 1. Compatibilidad de Versiones

- **Maven Wrapper**: 3.6.3
- **Proyecto**: Java 8 + Spring Boot 2.7
- **Simulación**: Entorno legacy antiguo

### 2. No Necesitas Instalar Maven

El Maven Wrapper (`mvnw`) descarga automáticamente Maven 3.6.3 la primera vez que se ejecuta. **No necesitas tener Maven instalado en tu sistema**.

### 3. Primera Ejecución

La primera vez que ejecutes `./mvnw`, descargará:
-Maven 3.6.3 (~9 MB)
-Dependencias del proyecto

Esto puede tardar unos minutos. Las siguientes ejecuciones serán más rápidas.

### 4. Verificar JAVA_HOME

El `mvnw` muestra automáticamente el JAVA_HOME al ejecutarse. También puedes verificar manualmente:

```bash
./mvnw -version
```

Esto mostrará la versión de Maven y el Java que está usando.

### 5. Windows

En Windows, usa `mvnw.cmd` en lugar de `./mvnw`:

```cmd
mvnw.cmd clean compile
mvnw.cmd spring-boot:run
```

## 🔍 Resolución de Problemas

### Error: "Java version mismatch"

```bash
# Verifica que el .env existe y tiene la ruta correcta
cat .env

# Verifica qué Java está usando
./mvnw -version
```

### Error: "Permission denied: ./mvnw"

```bash
# Dale permisos de ejecución
chmod +x mvnw
```

### Error: "Maven Wrapper not found"

```bash
# Descarga nuevamente el wrapper
curl -o mvnw https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw
chmod +x mvnw
```

## 📚 Comandos Útiles

```bash
# Ver información del proyecto
./mvnw help:effective-pom

# Ver dependencias
./mvnw dependency:tree

# Actualizar dependencias
./mvnw versions:display-dependency-updates

# Ejecutar un test específico
./mvnw test -Dtest=EmpresaServiceTest

# Ejecutar con perfil específico
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Saltar tests al compilar
./mvnw clean package -DskipTests

# Ver versión de Maven
./mvnw -version
```

## 🎯 Recomendaciones

1. **Para Desarrollo Local**: Simplemente usa `./mvnw` - carga automáticamente el `.env`
2. **Para CI/CD**: Configura las variables de entorno en tu pipeline
3. **Para Producción**: Considera usar Docker con Java 8
4. **Git**: El `maven-wrapper.jar` está incluido en el repositorio (es necesario)
5. **Configurar JDK**: Edita el archivo `.env` y ajusta `JAVA_PRJ_HOME` a tu instalación

## 📂 Estructura de Archivos

```text
proyecto/
├── .env                    # Variables de entorno (carga automática)
├── .gitignore             # Actualizado para Maven Wrapper
├── mvnw                   # Maven Wrapper modificado (carga .env)
├── mvnw.cmd              # Maven Wrapper (Windows)
├── pom.xml               # Configuración Maven
└── .mvn/
    └── wrapper/
        ├── maven-wrapper.jar
        └── maven-wrapper.properties
```

---

**Proyecto Legacy**: Java 8 + Spring Boot 2.7  
**Maven Wrapper**: 3.6.3  
**JDK Configurado**: `/opt/jdk-8` (ajustable en `.env`)
