package com.example.farmawell.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.dashboard.DetalleCompraDTO;
import com.example.farmawell.dto.dashboard.HistorialCompraDTO;
import com.example.farmawell.repository.DetalleVentaRepository;
import com.example.farmawell.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistorialCompraService {

    private final VentaRepository ventaRepository;

    private final DetalleVentaRepository detalleRepository;

    public List<HistorialCompraDTO> historialCliente(String codigo){

        return ventaRepository.historialCliente(codigo)
                .stream()
                .map(h -> HistorialCompraDTO.builder()
                        .numeroFactura(h.getNumeroFactura())
                        .fecha(h.getFecha())
                        .productos(h.getProductos())
                        .subtotal(h.getSubtotal())
                        .descuento(h.getDescuento())
                        .iva(h.getIva())
                        .total(h.getTotal())
                        .build())
                .toList();

    }

    public List<DetalleCompraDTO> detalleFactura(String factura){

        return detalleRepository.detalleFactura(factura)
                .stream()
                .map(d -> DetalleCompraDTO.builder()
                        .codigoProducto(d.getCodigoProducto())
                        .descripcion(d.getDescripcion())
                        .cantidad(d.getCantidad())
                        .precio(d.getPrecio())
                        .subtotal(d.getSubtotal())
                        .build())
                .toList();

    }

}