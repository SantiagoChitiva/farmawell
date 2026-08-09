package com.example.farmawell.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmawell.dto.dashboard.ClienteProductoDTO;
import com.example.farmawell.dto.dashboard.ProductoFavoritoDTO;
import com.example.farmawell.service.RecomendacionService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;
import com.example.farmawell.dto.dashboard.ProductoAfinidadDTO;

@RestController
@RequestMapping("/recomendaciones")
@RequiredArgsConstructor
public class RecomendacionController {

    private final RecomendacionService service;

    @GetMapping("/productos-favoritos")
    public List<ProductoFavoritoDTO> productosFavoritos(){

        return service.obtenerProductosFavoritos();

    }

    @GetMapping("/producto/{codigo}")
    public List<ClienteProductoDTO> clientesPorProducto(
            @PathVariable String codigo){

        return service.clientesInteresados(codigo);

    }

    @GetMapping("/afinidad/{codigo}")
    public List<ProductoAfinidadDTO> afinidad(
            @PathVariable String codigo,
            @RequestParam(defaultValue = "10") int limite){

        return service.obtenerAfinidad(codigo, limite);

    }

}