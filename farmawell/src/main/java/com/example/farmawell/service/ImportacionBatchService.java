package com.example.farmawell.service;

import com.example.farmawell.dto.excel.VentaExcelDTO;
import com.example.farmawell.entity.Cliente;
import com.example.farmawell.entity.Producto;
import com.example.farmawell.entity.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImportacionBatchService {

    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final VentaService ventaService;
    private final DetalleVentaService detalleVentaService;

    @Transactional
    public void procesarLote(List<VentaExcelDTO> lote) {
        for (VentaExcelDTO dto : lote) {
            Cliente cliente = clienteService.obtenerOCrear(dto);
            Producto producto = productoService.obtenerOCrear(dto);
            Venta venta = ventaService.obtenerOCrear(dto, cliente);
            detalleVentaService.guardar(venta, producto, dto);
        }
        detalleVentaService.finalizar(); // flush del buffer al cerrar el lote
    }
}
