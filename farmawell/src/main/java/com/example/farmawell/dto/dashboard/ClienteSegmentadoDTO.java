package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.farmawell.segment.SegmentoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteSegmentadoDTO {

    private String codigo;
    private String nombre;
    private String telefono;

    private Long compras;

    private BigDecimal totalComprado;

    private LocalDate ultimaCompra;

    private List<SegmentoCliente> segmentos;

}