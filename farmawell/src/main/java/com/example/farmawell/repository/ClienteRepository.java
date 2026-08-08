package com.example.farmawell.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.farmawell.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCodigoTns(String codigoTns);

}
