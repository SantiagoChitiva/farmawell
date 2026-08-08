package com.example.farmawell.projection;

import java.time.LocalDate;

public interface ClienteInactivoProjection {

    String getNombre();

    String getTelefono();

    LocalDate getUltimaCompra();

}