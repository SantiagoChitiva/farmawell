package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopProductoDTO {

    private String producto;
    private String marca;
    private Long cantidadVendida;
    private BigDecimal totalVendido;

}