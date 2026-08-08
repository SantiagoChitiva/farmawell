package com.example.farmawell.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.excel.VentaExcelDTO;
import com.example.farmawell.entity.Cliente;
import com.example.farmawell.entity.Producto;
import com.example.farmawell.entity.Venta;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImportacionService {

    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final VentaService ventaService;
    private final DetalleVentaService detalleVentaService;

    public void importar(List<VentaExcelDTO> ventas) {

    int contador = 0;

    int limite = Math.min(ventas.size(), 5000);

    for (int i = 0; i < limite; i++) {

        VentaExcelDTO dto = ventas.get(i);

        Cliente cliente = clienteService.obtenerOCrear(dto);

        Producto producto = productoService.obtenerOCrear(dto);

        Venta venta = ventaService.obtenerOCrear(dto, cliente);

        detalleVentaService.guardar(
                venta,
                producto,
                dto
        );

        contador++;

        if (contador % 1000 == 0) {
            System.out.println(
                    contador + " registros procesados"
            );
        }
    }

    detalleVentaService.finalizar();

    System.out.println("Importación terminada.");
}
}