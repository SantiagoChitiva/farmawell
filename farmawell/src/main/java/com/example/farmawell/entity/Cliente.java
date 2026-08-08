package com.example.farmawell.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_tns", unique = true)
    private String codigoTns;

    private String nombre;

    @Column(name = "telefono", length = 50)
    private String telefono;

    private String ciudad;

    private String direccion;

    private String barrio;

    private LocalDate fechaNacimiento;

    private String clasificacion;

    private Boolean activo;

    private LocalDate fechaRegistro;
}