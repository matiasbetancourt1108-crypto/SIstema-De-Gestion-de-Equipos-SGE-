# AGENTS.md

Instrucciones para agentes de IA que trabajan en este repositorio.

## Proyecto

- API REST en Java + Spring Boot 4.1.1, Java 17 (toolchain Gradle), build con Gradle.
- Group: `com.example`, Artifact: `demo`, Package: `com.example.demo`.

## Arquitectura

Paquetes bajo `com.example.demo` (separación por responsabilidad):

- `models/` — entidades JPA (`@Entity`) mapeadas a las tablas de MySQL `sge`. Getters/setters **manuales** (no Lombok). Usar `@Column(name = ...)` cuando el nombre difiera.
- `repositories/` — interfaces `JpaRepository` (`@Repository`). Sin SQL manual salvo necesidad.
- `services/` — lógica de negocio (`@Service`).
- `controllers/` — `@RestController` con `@RequestMapping("/api/<plural>")`.
- `config/` — `SecurityConfig` define el bean `PasswordEncoder` (BCrypt).
- `DemoApplication.java` queda en la raíz del paquete para que el component scan cubra los subpaquetes.

Tablas: `usuarios` (`id_usuario`, `nombre`, `email` único, `telefono`, `contrasena`), `categorias` (`id_categoria`, `nombre` único, `descripcion`), `equipos` (`id_equipo`, `nombre`, `marca`, `modelo`, `serial` único, `estado`, `id_categoria` FK) y `prestamos` (`id_prestamo`, `id_usuario` FK, `id_equipo` FK, `fecha_prestamo`, `fecha_devolucion`).

## Seguridad y contraseñas

- Las contraseñas se guardan **encriptadas con BCrypt**, nunca en texto plano.
- `Usuario` serializa sus campos en orden fijo con `@JsonPropertyOrder({ "id", "nombre", "email", "telefono", "contrasena" })` y **devuelve `contrasena` en las respuestas** (requisito del cliente); el valor expuesto es siempre el hash BCrypt, nunca el texto plano.
- `POST /api/usuarios/login` valida con `passwordEncoder.matches(...)`: `200` con id/nombre/email, `401` si falla.
- En PUT/PATCH solo se re-encripta si llega una contraseña nueva; si no llega, se conserva el hash existente.
- `POST /api/usuarios` exige contraseña (`400` si falta).

## Convenciones de la API

- CRUD completo por recurso: GET (lista), GET por id, POST, PUT, PATCH, DELETE.
- Respuestas con `ResponseEntity` + `HttpStatus` (`200 OK`, `201 CREATED`, `204 NO_CONTENT`, `404 NOT_FOUND`).
- Rutas para recursos "disponibles": `GET /api/<plural>/disponibles`.
- Nombres de campos en español (ej. `nombre`, `email`, `idCategoria`, `estado`).
- Manejo de PATCH con `Map<String, Object>` de campos a actualizar.
- Documentacion de endpoints con springdoc/OpenAPI: anotaciones `@Tag`, `@Operation`, `@ApiResponses`, `@ApiResponse`, `@Parameter`.

## Cómo ejecutar

- `./gradlew bootRun` (o `gradlew.bat` en Windows).

## Contexto del dominio

- SGE — Sistema de Gestion de Equipos: gestion de usuarios, equipos, categorias y prestamos/devoluciones.
- Controladores implementados: `UsuarioController` (`/api/usuarios` + `/login`), `CategoriaController` (`/api/categorias`), `EquipoController` (`/api/equipos` + `/buscar` + `/disponibles`) y `PrestamoController` (`/api/prestamos` + `/pendientes`).
- `Equipo` usa `estado` (`disponible` / `prestado`) y `idCategoria`; `/disponibles` filtra por `estado='disponible'`.

## Pruebas

- Probar con Postman o Insomnia contra el servidor levantado (`bootRun`).
- Documentacion Swagger via springdoc (ej. `/swagger-ui.html`, `/v3/api-docs`).
