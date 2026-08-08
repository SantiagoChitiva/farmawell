package com.example.farmawell.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface HistorialCompraProjection {

    String getNumeroFactura();

    LocalDate getFecha();

    Integer getProductos();

    BigDecimal getSubtotal();

    BigDecimal getDescuento();

    BigDecimal getIva();

    BigDecimal getTotal();
}