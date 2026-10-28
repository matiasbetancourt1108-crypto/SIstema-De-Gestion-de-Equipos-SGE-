# AGENTS.md

Instrucciones para agentes de IA que trabajan en este repositorio.

## Proyecto

- API REST en Java + Spring Boot 4.1.1, Java 17 (toolchain Gradle), build con Gradle.
- Group: `com.example`, Artifact: `demo`, Package: `com.example.demo`.

## Arquitectura

Cada entidad sigue la capa Repository → Service → Controller:

- `<Entidad>.java` — POJO simple (sin JPA/Lombok). `id` tipo `String` con UUID. Getters/setters **manuales** (no usar Lombok). Ej: `Equipo` con `id`, `nombre`, `categoria`, `descripcion`, `disponible`.
- `<Entidad>Repository.java` — `@Repository`. Persistencia simulada con `HashMap` en memoria (no hay BD real).
- `<Entidad>Service.java` — `@Service`. Orquesta la logica. Puede incluir `initSampleData()` con datos de prueba.
- `<Entidad>Controller.java` — `@RestController` con `@RequestMapping("/api/<plural>")`.

## Convenciones de la API

- CRUD completo por recurso: GET (lista), GET por id, POST, PUT, PATCH, DELETE.
- Respuestas con `ResponseEntity` + `HttpStatus` (`200 OK`, `201 CREATED`, `204 NO_CONTENT`, `404 NOT_FOUND`).
- Rutas para recursos "disponibles": `GET /api/<plural>/disponibles`.
- Nombres de campos en español (ej. `nombre`, `email`, `categoria`).
- Manejo de PATCH con `Map<String, Object>` de campos a actualizar.
- Documentacion de endpoints con springdoc/OpenAPI: anotaciones `@Tag`, `@Operation`, `@ApiResponses`, `@ApiResponse`, `@Parameter`.

## Cómo ejecutar

- `./gradlew bootRun` (o `gradlew.bat` en Windows).

## Contexto del dominio

- SGE — Sistema de Gestion de Equipos: gestion de usuarios, equipos, categorias y prestamos/devoluciones.
- Controlador de equipos implementado: `EquipoController` en `/api/equipos` (CRUD + Swagger + `/buscar` + `/disponibles`). Con `initSampleData()` en `EquipoService`.
- Pendiente: controladores de prestamos/devoluciones (`PrestamoController`).

## Pruebas

- Probar con Postman o Insomnia contra el servidor levantado (`bootRun`).
- Documentacion Swagger via springdoc (ej. `/swagger-ui.html`, `/v3/api-docs`).
