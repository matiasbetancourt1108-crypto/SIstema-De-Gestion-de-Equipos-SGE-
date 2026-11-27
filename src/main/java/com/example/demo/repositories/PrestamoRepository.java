package com.example.demo.repositories;

import com.example.demo.models.Prestamo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    List<Prestamo> findByFechaDevolucionIsNull();
}
