package com.example.farmawell.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoFavoritoDTO {

    private String nombreCliente;
    private String telefono;

    private String codigoProducto;
    private String descripcionProducto;

    private Long vecesComprado;
}