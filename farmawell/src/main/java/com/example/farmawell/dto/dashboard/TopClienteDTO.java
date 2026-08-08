package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopClienteDTO {

    private String nombre;
    private String telefono;
    private Long compras;
    private BigDecimal totalComprado;

}