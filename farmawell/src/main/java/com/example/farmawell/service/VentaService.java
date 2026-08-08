package com.example.farmawell.service;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.excel.VentaExcelDTO;
import com.example.farmawell.entity.Cliente;
import com.example.farmawell.entity.Venta;
import com.example.farmawell.repository.VentaRepository;
import com.example.farmawell.service.cache.ImportCache;
import com.example.farmawell.util.ExcelConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ImportCache cache;

    public Venta obtenerOCrear(
            VentaExcelDTO dto,
            Cliente cliente) {

        String factura = dto.getNumeroFactura();

        if (factura == null || factura.isBlank()) {
            return null;
        }

        factura = factura.trim();

        // 1. Buscar primero en memoria
        Venta venta = cache.getVentas().get(factura);

        if (venta != null) {
            return venta;
        }

        // 2. Buscar en la base de datos
        venta = ventaRepository.findByNumeroFactura(factura)
                .orElse(null);

        if (venta == null) {

            venta = Venta.builder()
                    .numeroFactura(factura)
                    .fecha(ExcelConverter.toLocalDate(dto.getFecha()))
                    .formaPago(dto.getFormaPago())
                    .tipoVenta(dto.getTipoVenta())
                    .sede(dto.getSede())
                    .subtotal(ExcelConverter.toBigDecimal(dto.getValorBase()))
                    .iva(ExcelConverter.toBigDecimal(dto.getIva()))
                    .descuento(ExcelConverter.toBigDecimal(dto.getDescuento()))
                    .total(ExcelConverter.toBigDecimal(dto.getTotal()))
                    .cliente(cliente)
                    .build();

            venta = ventaRepository.save(venta);
        }

        // 3. Guardar en caché
        cache.getVentas().put(factura, venta);

        return venta;
    }
}