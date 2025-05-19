# Proyecto E-commerce: Plataforma Avanzada - Ingeniería de Software II 🚀

![Visión del E-commerce](https://github.com/user-attachments/assets/5b93249d-6a88-4c84-9011-a35de35e6ce5)

## Información Académica 🎓
* **Asignatura:** Ingeniería de Software II
* **Docente:** Ing. Jathinson Meneses Mendoza
* **Institución:** Universidad Industrial de Santander (UIS)
* **Semestre:** 2025-1

## Descripción del Proyecto 📝
Este proyecto se centra en el desarrollo de una **plataforma de E-commerce avanzada y robusta**, concebida para simular la funcionalidad, escalabilidad y experiencia de usuario de referentes en la industria como Amazon y eBay. El sistema resultante es una tienda en línea completa que permite a los usuarios:
* Navegar y buscar productos eficientemente.
* Gestionar un carrito de compras dinámico.
* Aplicar descuentos y promociones.
* Simular transacciones de forma segura.

Desde la perspectiva administrativa, la plataforma sienta las bases para herramientas de gestión de inventario, procesamiento de pedidos y generación de reportes de ventas.

La arquitectura del sistema es un pilar fundamental, implementada mediante **microservicios** para el backend y **microfrontends** para la interfaz de usuario. Esta elección promueve la modularidad, escalabilidad independiente, mantenibilidad a largo plazo y la posibilidad de que diferentes equipos trabajen en paralelo con tecnologías especializadas. La comunicación asíncrona entre microservicios se gestiona principalmente a través de **RabbitMQ**, mientras que **gRPC** se considera para comunicación interna eficiente.

Para una comprensión más profunda del diseño y las decisiones arquitectónicas, puedes consultar nuestro [**Documento de Arquitectura detallado aquí**](https://docs.google.com/document/d/1FHYYW-qhqiEXfr3-2ML_er8SQ6CPBBDO_D7LfEwyWII/edit?usp=sharing).

El seguimiento del progreso, la planificación de actividades y el backlog detallado con las historias de usuario se encuentran en nuestro [**Tablero de Jira**](https://datz.atlassian.net/jira/software/c/projects/MCDC/boards/70/backlog?atlOrigin=eyJpIjoiZGJiODA5Y2MzZTgzNGVkY2JhNjVkMWMwNjEyNGEzZGUiLCJwIjoiaiJ9).

## Estado Actual del Proyecto 🎯
Actualmente, el proyecto se encuentra en la **fase final de integración y preparación para el despliegue** de su primera versión completa. Las funcionalidades clave de los microservicios desarrollados por este grupo han sido implementadas y se está trabajando en las pruebas E2E y la configuración para el despliegue final programado para el **27 de mayo**.

## Componentes Clave Desarrollados por Nuestro Grupo 🛠️
Dentro del ecosistema global del e-commerce, nuestro equipo ha sido responsable del diseño, desarrollo e integración de los siguientes microservicios y sus componentes de frontend asociados:
* **Microservicio de Carrito de Compras:** Gestión completa de las sesiones de compra de los usuarios, persistencia en Redis y lógica de negocio asociada.
* **Microservicio de Seguridad:** Implementación de mecanismos de autenticación (basado en JWT con Keycloak) y autorización para proteger los endpoints y recursos del sistema.
* **Microservicio de Descuentos:** Diseño de la lógica y API para la gestión y aplicación de descuentos en el carrito de compras.

## Tecnologías Utilizadas 💻
La plataforma emplea un stack tecnológico moderno y robusto:

* **Arquitectura General:**
    * Microservicios (Backend)
    * Microfrontends (Frontend)
    * API Gateway: **Kong API Gateway**
    * Broker de Mensajería: **RabbitMQ** (para AMQP)
    * Comunicación Interna Síncrona: **gRPC** (considerado)
* **Backend:**
    * Framework Principal: **Spring Boot**
    * Lenguaje: **Java** (JDK 24)
    * Gestor de Proyectos/Dependencias: **Maven**
* **Frontend:**
    * Framework Principal: **Angular**
    * Lenguaje: **TypeScript**
* **Bases de Datos:**
    * Carrito de Compras: **Redis**
    * Catálogo de Productos (desarrollado por otro grupo): **MongoDB**
    * Órdenes y Descuentos (propuesta/desarrollo): Bases de datos relacionales como **PostgreSQL / MySQL**
* **Seguridad:**
    * Servidor de Identidad: **Keycloak** (OAuth2, JWT)
* **Infraestructura y Contenerización:**
    * Contenedores: **Docker**
    * Orquestación (propuesta/futuro): **Kubernetes**
* **Control de Versiones:**
    * **Git** y **GitHub**

## Estructura del Repositorio y Flujo de Trabajo 📂
Este repositorio sigue un flujo de trabajo adaptado de Gitflow:
* `main`: Contiene la versión más estable del proyecto, idealmente código de las entregas o listo para producción.
* `develop`: Rama principal de integración continua. Todas las nuevas funcionalidades y desarrollos se fusionan aquí antes de pasar a `main`.
* `feature/<nombre-descriptivo>`: Ramas para el desarrollo de nuevas características o historias de usuario, creadas a partir de `develop`.
* `fix/<nombre-descriptivo>`: Ramas para correcciones de errores.
* `release/<version>`: Ramas para la preparación de nuevas versiones de producción.

**Commits Semánticos:** Fomentamos el uso de commits semánticos para mejorar la legibilidad del historial y facilitar la automatización de tareas (ej. generación de changelogs).

## Alcance de este Repositorio 🎯
Este repositorio contiene el código fuente y la configuración del proyecto padre Maven que agrupa los siguientes microservicios desarrollados por nuestro equipo:
* `microservicio-carrito`
* `microservicio-seguridad`
* `microservicio-descuentos`
* Archivos de configuración para el despliegue conjunto (ej. `docker-compose.yml`).
* Documentación relevante generada por el equipo.

## Cómo Empezar (Guía de Configuración Inicial) 🚀

1.  **Prerrequisitos:**
    * Git
    * Java JDK 24 o superior (Asegúrate de que la variable `JAVA_HOME` esté configurada)
    * Apache Maven 3.6+
    * Node.js y Angular CLI
    * Docker y Docker Compose
2.  **Clonar el repositorio:**
    ```bash
    git clone [URL-DEL-REPOSITORIO-EN-GITHUB]
    cd [NOMBRE-DEL-REPOSITORIO]
    ```
3.  **Rama de Desarrollo:**
    Asegúrate de estar en la rama `develop` para obtener los últimos cambios en desarrollo:
    ```bash
    git checkout develop
    git pull origin develop
    ```
4.  **Configuración del Backend (Microservicios):**
    * Cada microservicio es un módulo Maven independiente (ej. `microservicio-carrito/`).
    * **Construir el proyecto padre y los módulos:** Desde la raíz del repositorio clonado:
        ```bash
        mvn clean install
        ```
    * **Ejecutar un microservicio individualmente:**
        Navega al directorio del microservicio (ej. `cd microservicio-carrito`) y ejecuta:
        ```bash
        mvn spring-boot:run
        ```
    * **Variables de Entorno:** Revisa el archivo `application.properties` o `application.yml` de cada microservicio. Algunas configuraciones (como URLs de RabbitMQ, Redis, bases de datos, secretos de Keycloak) pueden requerir ser externas o configuradas a través de variables de entorno, especialmente para entornos Docker o de producción. Consulta la documentación específica de cada microservicio (`README.md` interno).
      
5.  **Configuración del Frontend (Microfrontends):**
    * Navega al directorio del microfrontend correspondiente.
    * Instala las dependencias:
        ```bash
        npm install
        ```
    * Ejecuta en modo desarrollo:
        ```bash
        ng serve
        ```
6.  **Levantar el Entorno Completo con Docker Compose (Recomendado para desarrollo y pruebas de integración):**
    Asegúrate de tener Docker y Docker Compose instalados y en ejecución. Desde la raíz del repositorio:
    ```bash
    docker-compose up --build
    ```
    Esto debería levantar todos los microservicios del grupo, junto con dependencias como RabbitMQ, Redis y una instancia de MongoDB (para simular el catálogo si está configurado así en el compose). Revisa el archivo `docker-compose.yml` para ver los servicios incluidos y los puertos expuestos.

## Documentación Adicional 📚
* [Documento de Arquitectura](https://docs.google.com/document/d/1FHYYW-qhqiEXfr3-2ML_er8SQ6CPBBDO_D7LfEwyWII/edit?usp=sharing)
* [Backlog en Jira](https://datz.atlassian.net/jira/software/c/projects/MCDC/boards/70/backlog?atlOrigin=eyJpIjoiZGJiODA5Y2MzZTgzNGVkY2JhNjVkMWMwNjEyNGEzZGUiLCJwIjoiaiJ9)
* [Informe Final del Proyecto](https://docs.google.com/document/d/1H_0sHoLoEsCblrogFdynmzkn8xHtTE1QZAuBV-OmeaI/edit?tab=t.0)

---

Este README es un documento vivo y se actualizará para reflejar la evolución del proyecto.
¡Cualquier contribución, sugerencia o issue es bienvenido! ✨
