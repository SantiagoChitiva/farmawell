package com.example.farmawell.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmawell.dto.dashboard.DetalleCompraDTO;
import com.example.farmawell.dto.dashboard.HistorialCompraDTO;
import com.example.farmawell.service.HistorialCompraService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/historial")
@RequiredArgsConstructor
public class HistorialCompraController {

    private final HistorialCompraService service;

    @GetMapping("/{codigoCliente}")
    public List<HistorialCompraDTO> historial(
            @PathVariable String codigoCliente){

        return service.historialCliente(codigoCliente);

    }

    @GetMapping("/factura/{factura}")
    public List<DetalleCompraDTO> detalle(
            @PathVariable String factura){

        return service.detalleFactura(factura);

    }

}