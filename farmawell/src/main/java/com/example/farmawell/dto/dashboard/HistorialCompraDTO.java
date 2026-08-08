package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistorialCompraDTO {

    private String numeroFactura;

    private LocalDate fecha;

    private Integer productos;

    private BigDecimal subtotal;

    private BigDecimal descuento;

    private BigDecimal iva;

    private BigDecimal total;

}