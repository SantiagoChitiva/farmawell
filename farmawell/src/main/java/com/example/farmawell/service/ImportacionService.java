package com.example.farmawell.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.excel.VentaExcelDTO;
import com.example.farmawell.service.cache.ImportCache;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImportacionService {

    private final ImportacionBatchService batchService;
    private static final int TAMANO_LOTE = 1000;

    private final ImportCache cache;

public void importar(List<VentaExcelDTO> ventas) {
    // Facturas que ya estaban en la base antes de esta importación
    Set<String> facturasPrevias = new HashSet<>(cache.getVentas().keySet());

    List<VentaExcelDTO> nuevas = ventas.stream()
            .filter(v -> v.getNumeroFactura() != null && !v.getNumeroFactura().isBlank())
            .filter(v -> !facturasPrevias.contains(v.getNumeroFactura().trim()))
            .toList();

    int total = nuevas.size();
    for (int i = 0; i < total; i += TAMANO_LOTE) {
        int fin = Math.min(i + TAMANO_LOTE, total);
        batchService.procesarLote(nuevas.subList(i, fin));
    }
}
}