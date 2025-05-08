# Proyecto E-commerce: Ingeniería de Software II

![ecommerce-768x473](https://github.com/user-attachments/assets/5b93249d-6a88-4c84-9011-a35de35e6ce5)

## Información Académica
-   **Asignatura:** Ingeniería de Software II
-   **Docente:** Jathinson Meneses Mendoza
-   **Institución:** Universidad Industrial de Santander

## Descripción del Proyecto
Este proyecto está enfocado en la creación de un **E-commerce avanzado y robusto**, diseñado para emular la funcionalidad y escalabilidad de plataformas de alta demanda como Amazon o Ebay. El sistema incorporará una tienda en línea que permitirá a los usuarios buscar productos de manera eficiente, gestionar su carrito de compras, aplicar descuentos y completar transacciones de forma segura. Por su parte, los administradores contarán con herramientas para la gestión de inventario en tiempo real, procesamiento de pedidos y generación de reportes de ventas.

La arquitectura del sistema se basa en **microservicios** para el backend y **microfrontends** para la interfaz de usuario, promoviendo la modularidad, la escalabilidad independiente de los componentes y la mantenibilidad a largo plazo. La comunicación entre microservicios se gestionará mediante **RabbitMQ**.

Adicionalmente, se proporciona documentación detallada del proyecto, incluyendo diagramas, el [backlog](https://datz.atlassian.net/jira/software/c/projects/MCDC/boards/70/backlog?atlOrigin=eyJpIjoiZGJiODA5Y2MzZTgzNGVkY2JhNjVkMWMwNjEyNGEzZGUiLCJwIjoiaiJ9) con las historias de usuario, la planificación de actividades y un cronograma de sprints para facilitar tanto la implementación como el seguimiento del progreso.

## Estado Actual del Proyecto
El proyecto se encuentra actualmente en **fase de desarrollo activo**.
-   **Próxima entrega (Funcionalidades Clave):** 20 de mayo.
-   **Entrega Final (Despliegue Completo):** 27 de mayo.

## Tecnologías Utilizadas
Para obtener mayor información sobre la arquitectura del proyecto consultar en este [enlace]( https://docs.google.com/document/d/1FHYYW-qhqiEXfr3-2ML_er8SQ6CPBBDO_D7LfEwyWII/edit?usp=sharing
).
-   **Arquitectura General:**
    -   Microservicios (Backend)
    -   Microfrontends (Frontend)
    -   Broker de Mensajería (Comunicación Asíncrona): RabbitMQ
-   **Backend:**
    -   Framework Principal: Spring Boot
    -   Gestor de Proyectos y Dependencias: Maven
    -   Lenguaje: Java
    -   
-   **Frontend:**
    -   Framework Principal: Angular
    -   Lenguaje: TypeScript
    -  
-   **Base de Datos:**
    -   Redis
-   **Control de Versiones:**
    -   Git
    -   GitHub

## Estructura del Repositorio y Flujo de Trabajo
Este repositorio sigue un flujo de trabajo basado en Gitflow (adaptado):
-   `main`: Contiene la versión más estable del proyecto. Idealmente, refleja el código de las entregas o versiones listas para un posible despliegue.
-   `develop`: Es la rama principal de integración continua. Todas las nuevas funcionalidades y desarrollos de los diferentes microservicios y microfrontends se fusionan aquí tras ser completadas y probadas en sus respectivas ramas de funcionalidad.
-   `feature/<nombre-descriptivo>`: Ramas específicas para el desarrollo de nuevas características, historias de usuario o componentes. Se crean a partir de `develop` y se fusionan de nuevo a `develop` mediante Pull Requests.

## Enfoque del Sprint Actual
Actualmente, el equipo (Grupo A - Carrito, Seguridad, Descuentos) está centrado en las siguientes tareas prioritarias para el sprint que finaliza el **20 de mayo**:
-   **Microservicio de Carrito de Compras:**
    -   Implementación de funcionalidades CRUD (Crear, Leer, Actualizar, Eliminar) para el carrito.
    -   Verificación de disponibilidad de productos al agregar al carrito.
    -   Integración con el manejo de concurrencia (ej. con Redis).
    -   Consumo eficiente de APIs relacionadas.
    -   Actualización de inventario en tiempo real.
-   **Microservicio de Seguridad:**
    -   Implementación de mecanismos de autenticación y autorización (ej. JWT).
    -   Prevención de ataques y fraudes comunes.
-   **Microservicio de Descuentos:**
    -   Diseño e implementación de la lógica para aplicar descuentos a productos o carritos.
-   **Conexión Backend-Frontend:** Establecer y probar la comunicación entre los microfrontends y sus respectivos microservicios.
-   **Integración entre Microservicios:** Asegurar la correcta comunicación y flujo de datos entre los microservicios desarrollados.

## Mockups y Prototipos
La información visual y los prototipos del diseño de la interfaz de usuario se pueden encontrar en:
-   

## Cómo Empezar (Guía de Configuración Inicial)
1.  **Clonar el repositorio:**
    ```bash
    git clone [URL-del-repositorio-en-GitHub]
    cd [nombre-del-repositorio]
    ```
2.  **Cambiar a la rama de desarrollo:**
    ```bash
    git checkout develop
    ```
3.  **Configuración del Backend (por cada microservicio):**
    -   Asegúrate de tener Java JDK (versión 24) y Maven instalados.
    -   Navega al directorio del microservicio.
    -   Instrucciones para configurar variables de entorno, bases de datos locales (si aplica), etc.
    -   Comando para compilar y ejecutar: `mvn spring-boot:run` (o similar).
4.  **Configuración del Frontend (por cada microfrontend):**
    -   Asegúrate de tener Node.js (versión X.X) y Angular CLI instalados.
    -   Navega al directorio del microfrontend.
    -   Instala las dependencias: `npm install` o `yarn install`.
    -   Comando para ejecutar en modo desarrollo: `ng serve`.

---

Este README es un documento vivo y se actualizará continuamente para reflejar el estado y la evolución del proyecto. 
¡Cualquier sugerencia para mejorarlo es bienvenida!
