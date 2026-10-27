package com.example.demo;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipoService {
    private final EquipoRepository equipoRepository;

    @Autowired
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
        initSampleData();
    }

    private void initSampleData() {
        save(new Equipo("Laptop HP", "Computador", "Portatil de oficina"));
        save(new Equipo("Monitor LG", "Monitor", "Monitor 24 pulgadas"));
        save(new Equipo("Teclado Mecanico", "Teclado", "Teclado retroiluminado"));
    }

    public Equipo save(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Equipo findById(String id) {
        return equipoRepository.findById(id);
    }

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    public List<Equipo> findByDisponible(boolean disponible) {
        return equipoRepository.findByDisponible(disponible);
    }

    public Equipo update(Equipo equipo) {
        return equipoRepository.update(equipo);
    }

    public Equipo patch(String id, Map<String, Object> updates) {
        Equipo equipo = equipoRepository.findById(id);
        if (equipo != null) {
            if (updates.containsKey("nombre")) {
                equipo.setNombre((String) updates.get("nombre"));
            }
            if (updates.containsKey("categoria")) {
                equipo.setCategoria((String) updates.get("categoria"));
            }
            if (updates.containsKey("descripcion")) {
                equipo.setDescripcion((String) updates.get("descripcion"));
            }
            if (updates.containsKey("disponible")) {
                equipo.setDisponible((Boolean) updates.get("disponible"));
            }
            return equipoRepository.update(equipo);
        }
        return null;
    }

    public void deleteById(String id) {
        equipoRepository.deleteById(id);
    }
}
