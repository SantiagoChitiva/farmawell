package com.example.farmawell.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmawell.dto.dashboard.ClienteProductoDTO;
import com.example.farmawell.dto.dashboard.ClienteSegmentadoDTO;
import com.example.farmawell.service.CampañaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/campanas")
@RequiredArgsConstructor
public class CampañaController {

    private final CampañaService campañaService;

    @GetMapping("/vip")
    public List<ClienteSegmentadoDTO> vip() {
        return campañaService.obtenerVip();
    }

    @GetMapping("/en-riesgo")
    public List<ClienteSegmentadoDTO> enRiesgo() {
        return campañaService.obtenerEnRiesgo();
    }

    @GetMapping("/recuperables")
    public List<ClienteSegmentadoDTO> recuperables() {
        return campañaService.obtenerRecuperables();
    }

    @GetMapping("/producto/{codigo}")
    public List<ClienteProductoDTO> porProducto(@PathVariable String codigo) {
        return campañaService.obtenerPorProducto(codigo);
    }
}

