package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class EquipoRepository {
    // Simulamos una base de datos con un Map
    private final Map<String, Equipo> baseDeDatos = new HashMap<>();

    public Equipo save(Equipo equipo) {
        baseDeDatos.put(equipo.getId(), equipo);
        return equipo;
    }

    public Equipo findById(String id) {
        return baseDeDatos.get(id);
    }

    public List<Equipo> findAll() {
        return new ArrayList<>(baseDeDatos.values());
    }

    public List<Equipo> findByDisponible(boolean disponible) {
        return baseDeDatos.values().stream()
                .filter(equipo -> equipo.isDisponible() == disponible)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        baseDeDatos.remove(id);
    }

    public Equipo update(Equipo equipo) {
        if (baseDeDatos.containsKey(equipo.getId())) {
            baseDeDatos.put(equipo.getId(), equipo);
            return equipo;
        }
        return null;
    }
}
