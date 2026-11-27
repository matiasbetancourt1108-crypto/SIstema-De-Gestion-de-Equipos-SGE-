package com.example.demo.controllers;

import com.example.demo.models.Prestamo;
import com.example.demo.services.PrestamoService;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prestamos")
@Tag(name = "Prestamos", description = "API para la gestión de préstamos y devoluciones de equipos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    @Autowired
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @Operation(
            summary = "Obtener todos los préstamos",
            description = "Devuelve una lista de todos los préstamos registrados.")
    @GetMapping
    public ResponseEntity<List<Prestamo>> getAllPrestamos() {
        List<Prestamo> prestamos = prestamoService.findAll();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener préstamos pendientes",
            description = "Devuelve los préstamos que aún no han sido devueltos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos pendientes obtenida correctamente")
    })
    @GetMapping("/pendientes")
    public ResponseEntity<List<Prestamo>> getPendientes() {
        List<Prestamo> prestamos = prestamoService.findPendientes();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar préstamo por ID",
            description = "Busca un préstamo mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getPrestamoById(@PathVariable Integer id) {
        Prestamo prestamo = prestamoService.findById(id);
        if (prestamo != null) {
            return new ResponseEntity<>(prestamo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Crear préstamo",
            description = "Registra un nuevo préstamo. Si no se indica la fecha de préstamo se usa la fecha actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Préstamo creado correctamente")
    })
    @PostMapping
    public ResponseEntity<Prestamo> createPrestamo(@RequestBody Prestamo prestamo) {
        Prestamo newPrestamo = prestamoService.save(prestamo);
        return new ResponseEntity<>(newPrestamo, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar préstamo",
            description = "Actualiza los datos de un préstamo existente (por ejemplo, para registrar la devolución).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> updatePrestamo(@PathVariable Integer id, @RequestBody Prestamo prestamo) {
        Prestamo existingPrestamo = prestamoService.findById(id);
        if (existingPrestamo != null) {
            prestamo.setId(id);
            Prestamo updatedPrestamo = prestamoService.update(prestamo);
            return new ResponseEntity<>(updatedPrestamo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Modificar parcialmente un préstamo",
            description = "Actualiza algunos campos de un préstamo existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo modificado correctamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Prestamo> patchUpdatePrestamo(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        Prestamo updatedPrestamo = prestamoService.patch(id, updates);
        if (updatedPrestamo != null) {
            return new ResponseEntity<>(updatedPrestamo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Eliminar préstamo",
            description = "Elimina un préstamo mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Préstamo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestamo(@PathVariable Integer id) {
        Prestamo existingPrestamo = prestamoService.findById(id);
        if (existingPrestamo != null) {
            prestamoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
