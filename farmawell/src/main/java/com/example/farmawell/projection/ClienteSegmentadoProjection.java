package com.example.farmawell.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ClienteSegmentadoProjection {

    String getCodigo();

    String getNombre();

    String getTelefono();

    Long getCompras();

    BigDecimal getTotalComprado();

    LocalDate getUltimaCompra();

}