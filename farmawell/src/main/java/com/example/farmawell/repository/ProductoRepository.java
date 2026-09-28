package com.example.farmawell.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.farmawell.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigoTns(String codigoTns);

    List<Producto> findTop10ByDescripcionContainingIgnoreCase(String texto);

}