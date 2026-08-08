package com.example.farmawell.projection;

import java.math.BigDecimal;

public interface TopClienteProjection {

    String getNombre();

    String getTelefono();

    Long getCompras();

    BigDecimal getTotalComprado();

}