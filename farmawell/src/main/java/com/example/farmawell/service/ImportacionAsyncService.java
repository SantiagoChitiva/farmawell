package com.example.farmawell.service;

import com.example.farmawell.dto.excel.VentaExcelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.farmawell.importer.ExcelImporter;


@Service
@RequiredArgsConstructor
public class ImportacionAsyncService {

    private final ExcelImporter excelImporter;
    private final ImportacionService importacionService;

    private volatile String estado = "SIN_INICIAR";

    @Async
    public void ejecutar(String ruta) {
        estado = "EN_PROGRESO";
        try {
            List<VentaExcelDTO> ventas = excelImporter.importar(ruta);
            importacionService.importar(ventas);
            estado = "TERMINADO";
        } catch (Exception e) {
            estado = "ERROR: " + e.getMessage();
        }
    }

    public String getEstado() {
        return estado;
    }
}
