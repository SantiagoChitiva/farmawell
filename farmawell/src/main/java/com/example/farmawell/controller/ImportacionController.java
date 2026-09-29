package com.example.farmawell.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.farmawell.repository.VentaRepository;
import com.example.farmawell.service.ImportacionAsyncService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;

import java.nio.file.Files;

@RestController
@RequestMapping("/importacion")
@RequiredArgsConstructor
public class ImportacionController {

    private final ImportacionAsyncService importacionAsyncService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public String importar(@RequestParam("archivo") MultipartFile archivo) throws IOException {

    String nombre = archivo.getOriginalFilename();
    if (archivo.isEmpty() || nombre == null || !nombre.toLowerCase().endsWith(".xlsx")) {
        return "Sube un archivo .xlsx válido.";
    }
    if ("EN_PROGRESO".equals(importacionAsyncService.getEstado())) {
        return "Ya hay una importación en curso.";
    }

    // Se guarda en un temporal; el servicio async lo borra al terminar
    Path temporal = Files.createTempFile("importacion-", ".xlsx");
    archivo.transferTo(temporal);

    importacionAsyncService.ejecutar(temporal.toString());
    return "Importación iniciada. Consulta /importacion/estado para ver el progreso.";
}

    @GetMapping("/estado")
    public String estado() {
        return importacionAsyncService.getEstado();
    }
}