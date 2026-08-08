package com.example.farmawell.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmawell.dto.dashboard.ClientePerfilDTO;
import com.example.farmawell.service.PerfilClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final PerfilClienteService perfilClienteService;

    @GetMapping("/{codigo}")
    public ClientePerfilDTO perfil(@PathVariable String codigo) {
        return perfilClienteService.obtenerPerfil(codigo);
    }
}