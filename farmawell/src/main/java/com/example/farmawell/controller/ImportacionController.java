package com.example.farmawell.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmawell.repository.VentaRepository;
import com.example.farmawell.service.ImportacionAsyncService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/importacion")
@RequiredArgsConstructor
public class ImportacionController {

    private final ImportacionAsyncService importacionAsyncService;
    private final VentaRepository ventaRepository;

    @PostMapping
    public String importar() {

        if (ventaRepository.count() > 0) {
            return "La base de datos ya contiene información.";
        }

        Path ruta = Paths.get(
                "excel",
                "BIOPENTA BASE DATOS VENTAS ENE 2025 A MAY 2026.xlsx")
                .toAbsolutePath();

        importacionAsyncService.ejecutar(ruta.toString());

        return "Importación iniciada en segundo plano. Consulta /importacion/estado para ver el progreso.";
    }

    @GetMapping("/estado")
    public String estado() {
        return importacionAsyncService.getEstado();
    }
}