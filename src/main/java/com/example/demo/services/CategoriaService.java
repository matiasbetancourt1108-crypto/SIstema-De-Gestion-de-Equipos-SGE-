package com.example.demo.services;

import com.example.demo.models.Categoria;
import com.example.demo.repositories.CategoriaRepository;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria findById(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> findByNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Categoria update(Categoria categoria) {
        Categoria existingCategoria = categoriaRepository.findById(categoria.getId()).orElse(null);
        if (existingCategoria != null) {
            existingCategoria.setNombre(categoria.getNombre());
            existingCategoria.setDescripcion(categoria.getDescripcion());
            return categoriaRepository.save(existingCategoria);
        }
        return null;
    }

    public Categoria patch(Integer id, Map<String, Object> updates) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria != null) {
            if (updates.containsKey("nombre")) {
                categoria.setNombre((String) updates.get("nombre"));
            }
            if (updates.containsKey("descripcion")) {
                categoria.setDescripcion((String) updates.get("descripcion"));
            }
            return categoriaRepository.save(categoria);
        }
        return null;
    }

    public void deleteById(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
