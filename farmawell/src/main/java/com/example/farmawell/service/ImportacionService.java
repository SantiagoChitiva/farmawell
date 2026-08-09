package com.example.farmawell.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.excel.VentaExcelDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImportacionService {

    private final ImportacionBatchService batchService;
    private static final int TAMANO_LOTE = 1000;

    public void importar(List<VentaExcelDTO> ventas) {
        int total = ventas.size();
        for (int i = 0; i < total; i += TAMANO_LOTE) {
            int fin = Math.min(i + TAMANO_LOTE, total);
            batchService.procesarLote(ventas.subList(i, fin));
            System.out.println((fin) + "/" + total + " registros procesados");
        }
        System.out.println("Importación terminada.");
    }
}