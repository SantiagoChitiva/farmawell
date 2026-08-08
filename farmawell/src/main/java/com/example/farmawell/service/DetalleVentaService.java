package com.example.farmawell.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.excel.VentaExcelDTO;
import com.example.farmawell.entity.DetalleVenta;
import com.example.farmawell.entity.Producto;
import com.example.farmawell.entity.Venta;
import com.example.farmawell.repository.DetalleVentaRepository;
import com.example.farmawell.util.ExcelConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {

    private final DetalleVentaRepository repository;

    private final List<DetalleVenta> buffer = new ArrayList<>();

    private static final int BATCH_SIZE = 500;

    public void guardar(
            Venta venta,
            Producto producto,
            VentaExcelDTO dto) {

        DetalleVenta detalle = DetalleVenta.builder()
                .venta(venta)
                .producto(producto)
                .cantidad(ExcelConverter.toInteger(dto.getCantidad()))
                .precio(ExcelConverter.toBigDecimal(dto.getTotal()))
                .subtotal(ExcelConverter.toBigDecimal(dto.getValorBase()))
                .build();

        buffer.add(detalle);

        if (buffer.size() >= BATCH_SIZE) {

            repository.saveAll(buffer);

            buffer.clear();
        }
    }

    public void finalizar() {

        if (!buffer.isEmpty()) {

            repository.saveAll(buffer);

            buffer.clear();
        }
    }
}