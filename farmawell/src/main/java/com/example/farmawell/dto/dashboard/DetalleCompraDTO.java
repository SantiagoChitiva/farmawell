package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetalleCompraDTO {

    private String codigoProducto;

    private String descripcion;

    private Integer cantidad;

    private BigDecimal precio;

    private BigDecimal subtotal;

}