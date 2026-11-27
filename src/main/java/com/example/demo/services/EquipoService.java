package com.example.demo.services;

import com.example.demo.models.Equipo;
import com.example.demo.repositories.EquipoRepository;

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
    }

    public Equipo save(Equipo equipo) {
        if (equipo.getEstado() == null || equipo.getEstado().isBlank()) {
            equipo.setEstado("disponible");
        }
        return equipoRepository.save(equipo);
    }

    public Equipo findById(Integer id) {
        return equipoRepository.findById(id).orElse(null);
    }

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    public List<Equipo> findByEstado(String estado) {
        return equipoRepository.findByEstado(estado);
    }

    public Equipo update(Equipo equipo) {
        Equipo existingEquipo = equipoRepository.findById(equipo.getId()).orElse(null);
        if (existingEquipo != null) {
            existingEquipo.setNombre(equipo.getNombre());
            existingEquipo.setMarca(equipo.getMarca());
            existingEquipo.setModelo(equipo.getModelo());
            existingEquipo.setSerial(equipo.getSerial());
            if (equipo.getEstado() != null && !equipo.getEstado().isBlank()) {
                existingEquipo.setEstado(equipo.getEstado());
            }
            existingEquipo.setIdCategoria(equipo.getIdCategoria());
            return equipoRepository.save(existingEquipo);
        }
        return null;
    }

    public Equipo patch(Integer id, Map<String, Object> updates) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);
        if (equipo != null) {
            if (updates.containsKey("nombre")) {
                equipo.setNombre((String) updates.get("nombre"));
            }
            if (updates.containsKey("marca")) {
                equipo.setMarca((String) updates.get("marca"));
            }
            if (updates.containsKey("modelo")) {
                equipo.setModelo((String) updates.get("modelo"));
            }
            if (updates.containsKey("serial")) {
                equipo.setSerial((String) updates.get("serial"));
            }
            if (updates.containsKey("estado")) {
                equipo.setEstado((String) updates.get("estado"));
            }
            if (updates.containsKey("idCategoria")) {
                Object value = updates.get("idCategoria");
                equipo.setIdCategoria(value == null ? null
                        : (value instanceof Number numero ? numero.intValue()
                                : Integer.valueOf(value.toString())));
            }
            return equipoRepository.save(equipo);
        }
        return null;
    }

    public void deleteById(Integer id) {
        equipoRepository.deleteById(id);
    }
}
