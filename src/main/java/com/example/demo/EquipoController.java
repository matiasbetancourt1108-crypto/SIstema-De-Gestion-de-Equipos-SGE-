package com.example.demo;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    @Autowired
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public ResponseEntity<List<Equipo>> getAllEquipos() {
        List<Equipo> equipos = equipoService.findAll();
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Equipo>> getDisponibles() {
        List<Equipo> equipos = equipoService.findByDisponible(true);
        return new ResponseEntity<>(equipos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable String id) {
        Equipo equipo = equipoService.findById(id);
        if (equipo != null) {
            return new ResponseEntity<>(equipo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Equipo> createEquipo(@RequestBody Equipo equipo) {
        Equipo newEquipo = equipoService.save(equipo);
        return new ResponseEntity<>(newEquipo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> updateEquipo(@PathVariable String id, @RequestBody Equipo equipo) {
        Equipo existingEquipo = equipoService.findById(id);
        if (existingEquipo != null) {
            equipo.setId(id);
            Equipo updatedEquipo = equipoService.update(equipo);
            return new ResponseEntity<>(updatedEquipo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Equipo> patchUpdateEquipo(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        Equipo updatedEquipo = equipoService.patch(id, updates);
        if (updatedEquipo != null) {
            return new ResponseEntity<>(updatedEquipo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable String id) {
        Equipo existingEquipo = equipoService.findById(id);
        if (existingEquipo != null) {
            equipoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
