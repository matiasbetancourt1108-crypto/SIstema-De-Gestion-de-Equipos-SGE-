package com.example.demo;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipos")
@Tag(name = "Equipos", description = "API para la gestión de equipos")
public class EquipoController {
    private final EquipoService equipoService;

    @Autowired
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }
    @Operation(
            summary = "Obtener todos los equipos",
            description = "Devuelve una lista de todos los equipos registrados.")
    @GetMapping
    public ResponseEntity<List<Equipo>> getAllEquipos() {
        List<Equipo> equipos = equipoService.findAll();
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }
    @Operation(
            summary = "Obtener equipos disponibles",
            description = "Devuelve una lista de los equipos disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos disponibles obtenida correctamente")
    })
    @GetMapping("/disponibles")
    public ResponseEntity<List<Equipo>> getDisponibles() {
        List<Equipo> equipos = equipoService.findByDisponible(true);
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }
    @Operation(
            summary = "Buscar equipo por ID",
            description = "Busca un equipo mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getEquipoById(
            @PathVariable String id
    ) {
        Equipo equipo = equipoService.findById(id);
        if (equipo != null) {
            return new ResponseEntity<>(equipo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(
            summary = "Buscar equipos",
            description = "Busca equipos por nombre, categoría y disponibilidad.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<Equipo>> buscarEquipos(
            @Parameter(
                    description = "Nombre del equipo (parcial o completo)")
            @RequestParam(required = false) String nombre,
            @Parameter(description = "Categoría del equipo (opcional)")
            @RequestParam(required = false) String categoria,
            @Parameter(description = "Disponibilidad del equipo (true o false)")
            @RequestParam(required = false) Boolean disponible
    ) {
        List<Equipo> equipos = equipoService.findAll();
        List<Equipo> resultados = equipos.stream()
                .filter(e -> nombre == null ||
                        (e.getNombre() != null &&
                                e.getNombre().toLowerCase()
                                        .contains(nombre.toLowerCase())))
                .filter(e -> categoria == null ||
                        (e.getCategoria() != null &&
                                e.getCategoria().equalsIgnoreCase(categoria)))
                .filter(e -> disponible == null ||
                        e.isDisponible() == disponible)
                .toList();

        return ResponseEntity.ok(resultados);
    }
    @Operation( summary = "Crear equipo", description = "Registra un nuevo equipo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo creado correctamente")
    })
    @PostMapping
    public ResponseEntity<Equipo> createEquipo(
            @RequestBody Equipo equipo
    ) {
        Equipo newEquipo = equipoService.save(equipo);
        return new ResponseEntity<>(newEquipo, HttpStatus.CREATED);
    }
    @Operation(summary = "Actualizar equipo", description = "Actualiza los datos de un equipo existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Equipo> updateEquipo( @PathVariable String id, @RequestBody Equipo equipo
    ) {
        Equipo existingEquipo = equipoService.findById(id);
        if (existingEquipo != null) {
            equipo.setId(id);
            Equipo updatedEquipo = equipoService.update(equipo);
            return new ResponseEntity<>(updatedEquipo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Modificar parcialmente un equipo", description = "Actualiza algunos campos de un equipo existente.")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Equipo modificado correctamente"),
            @ApiResponse( responseCode = "404", description = "Equipo no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Equipo> patchUpdateEquipo(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates
    ) {
        Equipo updatedEquipo = equipoService.patch(id, updates);
        if (updatedEquipo != null) {
            return new ResponseEntity<>(updatedEquipo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Eliminar equipo", description = "Elimina un equipo mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "204", description = "Equipo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipo(
            @PathVariable String id
    ) {
        Equipo existingEquipo = equipoService.findById(id);
        if (existingEquipo != null) {
            equipoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

