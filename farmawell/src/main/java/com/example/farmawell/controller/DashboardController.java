package com.example.farmawell.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmawell.dto.dashboard.ClienteInactivoDTO;
import com.example.farmawell.dto.dashboard.ClienteSegmentadoDTO;
import com.example.farmawell.dto.dashboard.ClienteVipDTO;
import com.example.farmawell.dto.dashboard.DashboardResumenDTO;
import com.example.farmawell.dto.dashboard.TopClienteDTO;
import com.example.farmawell.dto.dashboard.TopProductoDTO;
import com.example.farmawell.service.DashboardService;
import com.example.farmawell.service.SegmentacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final SegmentacionService segmentacionService;

    @GetMapping("/dashboard/resumen")
    public DashboardResumenDTO resumen() {
        return dashboardService.obtenerResumen();
    }

    @GetMapping("/dashboard/top-clientes")
    public List<TopClienteDTO> topClientes(@RequestParam(defaultValue = "10")int limite){
        return dashboardService.obtenerTopClientes(limite);
    }

    @GetMapping("/dashboard/top-productos")
    public List<TopProductoDTO> topProductos(@RequestParam(defaultValue = "10") int limite){
        return dashboardService.obtenerTopProductos(limite);
}

@GetMapping("/dashboard/clientes-inactivos")
public List<ClienteInactivoDTO> clientesInactivos(

        @RequestParam(defaultValue = "90")
        int dias){

    return dashboardService.obtenerClientesInactivos(dias);

}

@GetMapping("/dashboard/clientes-vip")
public List<ClienteVipDTO> clientesVip(

        @RequestParam(defaultValue = "20")
        int limite){

    return dashboardService.obtenerClientesVip(limite);

}

@GetMapping("/dashboard/segmentacion")
public List<ClienteSegmentadoDTO> segmentacion(){

    return segmentacionService.obtenerSegmentacion();

}

}