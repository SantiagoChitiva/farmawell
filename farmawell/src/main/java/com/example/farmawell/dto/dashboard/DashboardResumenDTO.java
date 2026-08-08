package com.example.farmawell.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResumenDTO {

    private Long clientes;
    private Long productos;
    private Long ventas;
    private Long detalles;
}