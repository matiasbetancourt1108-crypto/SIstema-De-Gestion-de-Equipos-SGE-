package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 50)
    private String marca;

    @Column(length = 50)
    private String modelo;

    @Column(unique = true, length = 100)
    private String serial;

    @Column(nullable = false, length = 20)
    private String estado = "disponible";

    @Column(name = "id_categoria", nullable = false)
    private Integer idCategoria;

    // Constructor por defecto (necesario para JPA y frameworks como Jackson)
    public Equipo() {
    }

    // --- Getters ---

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSerial() {
        return serial;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    // --- Setters ---

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
}
