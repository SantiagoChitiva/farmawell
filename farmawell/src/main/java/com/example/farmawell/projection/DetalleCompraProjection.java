package com.example.farmawell.projection;

import java.math.BigDecimal;

public interface DetalleCompraProjection {

    String getCodigoProducto();

    String getDescripcion();

    Integer getCantidad();

    BigDecimal getPrecio();

    BigDecimal getSubtotal();
}