package com.example.farmawell.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmawell.dto.excel.VentaExcelDTO;
import com.example.farmawell.importer.ExcelImporter;
import com.example.farmawell.repository.VentaRepository;
import com.example.farmawell.service.ImportacionService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/importacion")
@RequiredArgsConstructor
public class ImportacionController {

    private final ExcelImporter excelImporter;
    private final ImportacionService importacionService;
    private final VentaRepository ventaRepository;

    @PostMapping
    public String importar() {

        // Verificar si ya hay datos importados
        if (ventaRepository.count() > 0) {
            return "La base de datos ya contiene información.";
        }

        Path ruta = Paths.get(
                "excel",
                "BIOPENTA BASE DATOS VENTAS ENE 2025 A MAY 2026.xlsx")
                .toAbsolutePath();

        List<VentaExcelDTO> ventas = excelImporter.importar(ruta.toString());


        System.out.println("TOTAL DTO LEÍDOS: " + ventas.size());

        importacionService.importar(ventas);

        return "Importación terminada correctamente.";
    }
}