package com.example.demo.services;

import com.example.demo.models.Prestamo;
import com.example.demo.repositories.PrestamoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoService {
    private final PrestamoRepository prestamoRepository;

    @Autowired
    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo save(Prestamo prestamo) {
        if (prestamo.getFechaPrestamo() == null) {
            prestamo.setFechaPrestamo(LocalDateTime.now());
        }
        return prestamoRepository.save(prestamo);
    }

    public Prestamo findById(Integer id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    public List<Prestamo> findPendientes() {
        return prestamoRepository.findByFechaDevolucionIsNull();
    }

    public Prestamo update(Prestamo prestamo) {
        Prestamo existingPrestamo = prestamoRepository.findById(prestamo.getId()).orElse(null);
        if (existingPrestamo != null) {
            existingPrestamo.setIdUsuario(prestamo.getIdUsuario());
            existingPrestamo.setIdEquipo(prestamo.getIdEquipo());
            if (prestamo.getFechaPrestamo() != null) {
                existingPrestamo.setFechaPrestamo(prestamo.getFechaPrestamo());
            }
            existingPrestamo.setFechaDevolucion(prestamo.getFechaDevolucion());
            return prestamoRepository.save(existingPrestamo);
        }
        return null;
    }

    public Prestamo patch(Integer id, Map<String, Object> updates) {
        Prestamo prestamo = prestamoRepository.findById(id).orElse(null);
        if (prestamo != null) {
            if (updates.containsKey("idUsuario")) {
                prestamo.setIdUsuario(toInteger(updates.get("idUsuario")));
            }
            if (updates.containsKey("idEquipo")) {
                prestamo.setIdEquipo(toInteger(updates.get("idEquipo")));
            }
            if (updates.containsKey("fechaPrestamo")) {
                LocalDateTime fechaPrestamo = parseFecha(updates.get("fechaPrestamo"));
                if (fechaPrestamo != null) {
                    prestamo.setFechaPrestamo(fechaPrestamo);
                }
            }
            if (updates.containsKey("fechaDevolucion")) {
                prestamo.setFechaDevolucion(parseFecha(updates.get("fechaDevolucion")));
            }
            return prestamoRepository.save(prestamo);
        }
        return null;
    }

    public void deleteById(Integer id) {
        prestamoRepository.deleteById(id);
    }

    private Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number numero) {
            return numero.intValue();
        }
        return Integer.valueOf(value.toString());
    }

    private LocalDateTime parseFecha(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDateTime fecha) {
            return fecha;
        }
        String texto = value.toString().trim();
        if (texto.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(texto);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(texto).atStartOfDay();
        }
    }
}
