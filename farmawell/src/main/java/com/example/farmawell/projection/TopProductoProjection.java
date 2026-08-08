package com.example.farmawell.projection;

import java.math.BigDecimal;

public interface TopProductoProjection {

    String getProducto();

    String getMarca();

    Long getCantidadVendida();

    BigDecimal getTotalVendido();

}