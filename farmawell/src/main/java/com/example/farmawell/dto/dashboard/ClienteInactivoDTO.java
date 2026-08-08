package com.example.farmawell.dto.dashboard;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClienteInactivoDTO {

    private String nombre;
    private String telefono;
    private LocalDate ultimaCompra;

}