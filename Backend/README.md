# Guía de Estructura y Convenciones para Microservicios Backend

## 1. Introducción
Esta guía define la estructura de directorios estándar y las convenciones de codificación recomendadas para cada microservicio dentro del proyecto E-commerce. Seguir estas directrices ayudará a mantener la coherencia, mejorar la legibilidad del código y facilitar la colaboración entre los miembros del equipo.

Cada microservicio es un módulo Maven independiente dentro del proyecto padre y debe seguir la estructura aquí descrita.

## 2. Estructura General de un Microservicio (Módulo Maven)

A continuación, se presenta la estructura de directorios típica para un microservicio Spring-Boot:

````
[nombre-del-microservicio]/  (Ej: microservicio-carrito, microservicio-seguridad)
├── pom.xml                     // Archivo de configuración de Maven para este módulo específico
└── src/
├── main/
│   ├── java/
│   │   └── co.edu.uis.sistemas.ecommerce.carrito/
│   │       ├── [NombreDelMicroservicio]Application.java // Clase principal @SpringBootApplication
│   │       │
│   │       ├── config/                   // Clases de Configuración (@Configuration)
│   │       │   ├── SecurityConfig.java   // Configuración de Spring Security
│   │       │   ├── RabbitMQConfig.java   // Configuración de Beans para RabbitMQ (Productores, etc.)
│   │       │   └── AppConfig.java        // Otras configuraciones de beans
│   │       │
│   │       ├── controller/ (o api/ o rest/) // Controladores REST API (@RestController)
│   │       │   └── ProductoController.java
│   │       │
│   │       ├── service/                  // Lógica de negocio (@Service)
│   │       │   ├── impl/                 // Implementaciones de las interfaces de servicio (opcional)
│   │       │   └── ProductoService.java
│   │       │
│   │       ├── repository/ (o dao/)      // Acceso a datos (@Repository), ej. Interfaces JPA
│   │       │   └── ProductoRepository.java
│   │       │
│   │       ├── model/ (o domain/ o entity/) // Entidades JPA (@Entity), Objetos de Dominio
│   │       │   └── Producto.java
│   │       │
│   │       ├── dto/                      // Data Transfer Objects (Solicitudes, Respuestas API)
│   │       │   ├── request/
│   │       │   │   └── ProductoRequestDTO.java
│   │       │   └── response/
│   │       │       └── ProductoResponseDTO.java
│   │       │
│   │       ├── exception/                // Excepciones personalizadas y manejadores globales
│   │       │   ├── GlobalExceptionHandler.java // @ControllerAdvice
│   │       │   └── ResourceNotFoundException.java
│   │       │
│   │       ├── util/                     // Clases de utilidad
│   │       │   └── DateUtil.java
│   │       │
│   │       └── listener/ (o consumer/)   // Consumidores de mensajes de RabbitMQ (@RabbitListener)
│   │           └── ProductoEventListener.java
│   │
│   └── resources/
│       ├── application.properties        // Configuración principal de la aplicación (o application.yml)
│       ├── application-dev.properties    // Configuración para el perfil de desarrollo
│       ├── application-prod.properties   // Configuración para el perfil de producción
│
└── test/
├── java/
│   └── [paquete.raiz.del.microservicio]/ // Misma estructura de paquetes que en main/java
│       ├── controller/
│       ├── service/
│       └── repository/
│
└── resources/
└── application-test.properties   // Configuración específica para pruebas (ej. H2)

````

## 3. Descripción de Paquetes Principales (`src/main/java`)

* **`[NombreDelMicroservicio]Application.java`**: Clase principal anotada con `@SpringBootApplication`. Es el punto de entrada para el microservicio.
* **`config/`**: Contiene clases de configuración de Spring anotadas con `@Configuration`. Aquí se definen beans, configuraciones de seguridad, configuración de RabbitMQ (productores, bindings, etc.), Swagger/OpenAPI, y otras configuraciones globales.
* **`controller/` (o `api/`, `rest/`)**: Clases anotadas con `@RestController`. Responsables de manejar las solicitudes HTTP entrantes, validar datos de entrada (a menudo usando DTOs), y delegar la lógica de negocio a la capa de servicio. Deben ser lo más "delgadas" posible.
* **`service/`**: Contiene la lógica de negocio principal del microservicio. Las clases aquí se anotan con `@Service`. Orquestan las interacciones entre los repositorios, otros servicios (si los hay) y pueden manejar transacciones. Se recomienda usar interfaces para los servicios y luego implementaciones en un subpaquete `impl/` si la complejidad lo amerita (`service/impl/`).
* **`repository/` (o `dao/`)**: Interfaces que definen las operaciones de acceso a datos. Típicamente, extienden interfaces de Spring Data JPA como `JpaRepository`, `MongoRepository`, etc. Anotadas con `@Repository`.
* **`model/` (o `domain/`, `entity/`)**: Clases que representan los datos persistentes (entidades JPA anotadas con `@Entity`) o los objetos de dominio centrales del microservicio.
* **`dto/` (Data Transfer Objects)**: Objetos Planos de Java (POJOs) utilizados para transferir datos entre capas, especialmente entre la capa de controlador y la de servicio, y como cuerpos de solicitud/respuesta en las APIs REST. Ayudan a desacoplar la estructura interna de tus entidades de la API pública. Se pueden organizar en subpaquetes `request/` y `response/`.
* **`exception/`**: Clases para excepciones personalizadas (ej. `ProductoNoEncontradoException`) y manejadores de excepciones globales (clases anotadas con `@ControllerAdvice` para estandarizar las respuestas de error de la API).
* **`util/`**: Clases de utilidad genéricas que pueden ser reutilizadas en diferentes partes del microservicio (ej. formateadores de fecha, validadores específicos no cubiertos por anotaciones estándar, etc.).
* **`listener/` (o `consumer/`)**: Clases responsables de consumir mensajes de colas de RabbitMQ. Usan la anotación `@RabbitListener` en los métodos que procesan los mensajes.

## 4. Archivos de Recursos (`src/main/resources`)

* **`application.properties` (o `application.yml`)**: Archivo principal de configuración de Spring Boot (puerto del servidor, configuración de base de datos, URLs de RabbitMQ, configuraciones personalizadas). Se prefiere `application.yml` por su estructura jerárquica y menor verbosidad para configuraciones complejas.
* **Perfiles (`application-[perfil].properties` o `.yml`)**: Permiten tener configuraciones específicas para diferentes entornos (ej. `dev`, `test`, `prod`). Se activan mediante la propiedad `spring.profiles.active`.

## 5. Pruebas (`src/test`)

* Mantener una estructura de paquetes paralela a `src/main/java` para las clases de prueba.
* **Pruebas Unitarias**: Para clases de servicio, utilidades, etc. (usando Mockito para simular dependencias).
* **Pruebas de Integración**: Para controladores (usando `@WebMvcTest` o `@SpringBootTest` con `MockMvc`), repositorios (usando `@DataJpaTest` y una base de datos en memoria como H2), y listeners de RabbitMQ.
* **`application-test.properties` (o `.yml`)**: Para configuraciones específicas del entorno de prueba (ej. base de datos en memoria, configuración de RabbitMQ para pruebas).

## 6. Convenciones y Buenas Prácticas Adicionales

* **Nomenclatura:**
    * Paquetes: `minúsculas.separadas.por.puntos`
    * Clases e Interfaces: `UpperCamelCase` (ej. `CarritoService`, `ProductoController`)
    * Métodos y Variables: `lowerCamelCase` (ej. `obtenerProductoPorId`, `nombreUsuario`)
    * Constantes: `UPPER_SNAKE_CASE` (ej. `MAX_INTENTOS_LOGIN`)
* **Inyección de Dependencias:** Preferir la inyección por constructor sobre la inyección por campo (`@Autowired` en el campo). Es más explícita y facilita las pruebas.
* **DTOs (Data Transfer Objects):**
    * Usar siempre DTOs para las entradas y salidas de tu API REST. No exponer entidades JPA directamente.
    * Validar los DTOs de entrada usando anotaciones de Bean Validation (ej. `@NotNull`, `@Size`, `@Email`).
    * Considerar el uso de bibliotecas como MapStruct para el mapeo entre Entidades y DTOs.
* **Manejo de Excepciones:**
    * Implementar un manejador de excepciones global (`@ControllerAdvice`) para capturar excepciones y devolver respuestas HTTP consistentes y bien formateadas.
    * Usar códigos de estado HTTP apropiados.
* **Variables de Entorno y Configuración:**
    * Evitar "hardcodear" configuraciones sensibles (credenciales, URLs de servicios externos) en los archivos `application.properties`. Utilizar variables de entorno o, para configuraciones más complejas, un servidor de configuración como Spring Cloud Config.
* **Logging:**
    * Utilizar SLF4J con Logback (configuración por defecto en Spring Boot).
    * Registrar información útil y contextual, especialmente en puntos críticos y al manejar errores. Evitar registrar datos sensibles.
* **Principios SOLID:** Esforzarse por aplicar los principios SOLID (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion) para un diseño de software más mantenible y robusto.
* **Comentarios y Documentación:**
    * Comentar el código cuando la lógica no sea obvia.
    * Usar Javadoc para documentar las clases y métodos públicos, especialmente en la capa de API y servicios.
    * Considerar el uso de Swagger/OpenAPI para documentar tus APIs REST (Springdoc OpenAPI es una buena opción).
* **Seguridad:**
    * Implementar las medidas de seguridad adecuadas utilizando Spring Security (autenticación, autorización, protección contra vulnerabilidades comunes como XSS, CSRF, SQL Injection).
* **Comunicación Asíncrona (RabbitMQ):**
    * Definir claramente los exchanges, colas y bindings.
    * Manejar los errores en los consumidores (ej. dead-letter queues).
    * Asegurar la idempotencia de los consumidores si es posible.

---
Esta guía es un punto de partida. Siéntanse libres de adaptarla y expandirla según las necesidades específicas del proyecto.
