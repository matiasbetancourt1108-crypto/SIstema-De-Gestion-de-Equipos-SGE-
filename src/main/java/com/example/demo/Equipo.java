package com.example.demo;

import java.util.UUID;

public class Equipo {
    private String id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private boolean disponible;

    public Equipo() {
        this.id = UUID.randomUUID().toString();
        this.disponible = true;
    }

    public Equipo(String nombre, String categoria, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.disponible = true;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
