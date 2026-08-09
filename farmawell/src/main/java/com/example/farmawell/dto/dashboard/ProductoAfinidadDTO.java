package com.example.farmawell.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoAfinidadDTO {

    private String codigoProducto;
    private String descripcion;
    private String marca;
    private Long vecesJuntos;

}