package com.example.farmawell.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoBusquedaDTO {

    private String codigo;
    private String descripcion;
    private String marca;

}