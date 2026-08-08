package com.example.farmawell.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.farmawell.dto.dashboard.ClienteInactivoDTO;
import com.example.farmawell.dto.dashboard.ClienteVipDTO;
import com.example.farmawell.dto.dashboard.DashboardResumenDTO;
import com.example.farmawell.dto.dashboard.TopClienteDTO;
import com.example.farmawell.dto.dashboard.TopProductoDTO;
import com.example.farmawell.repository.ClienteRepository;
import com.example.farmawell.repository.DetalleVentaRepository;
import com.example.farmawell.repository.ProductoRepository;
import com.example.farmawell.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public DashboardResumenDTO obtenerResumen() {

        return new DashboardResumenDTO(
                clienteRepository.count(),
                productoRepository.count(),
                ventaRepository.count(),
                detalleVentaRepository.count()
        );

    }

    public List<TopClienteDTO> obtenerTopClientes(int limite){

    Pageable pageable = PageRequest.of(0, limite);

    return ventaRepository
            .obtenerTopClientes(pageable)
            .stream()
            .map(p -> new TopClienteDTO(
                    p.getNombre(),
                    p.getTelefono(),
                    p.getCompras(),
                    p.getTotalComprado()))
            .toList();

    }

    public List<TopProductoDTO> obtenerTopProductos(int limite){

    Pageable pageable = PageRequest.of(0, limite);

    return detalleVentaRepository
            .obtenerTopProductos(pageable)
            .stream()
            .map(p -> new TopProductoDTO(
                    p.getProducto(),
                    p.getMarca(),
                    p.getCantidadVendida(),
                    p.getTotalVendido()))
            .toList();

    }

    public List<ClienteInactivoDTO> obtenerClientesInactivos(int dias){

    LocalDate fechaLimite = LocalDate.now().minusDays(dias);

    return ventaRepository
            .obtenerClientesInactivos(fechaLimite)
            .stream()
            .map(c -> new ClienteInactivoDTO(
                    c.getNombre(),
                    c.getTelefono(),
                    c.getUltimaCompra()))
            .toList();

}

public List<ClienteVipDTO> obtenerClientesVip(int limite){

    Pageable pageable = PageRequest.of(0, limite);

    return ventaRepository
            .obtenerClientesVip(pageable)
            .stream()
            .map(c -> new ClienteVipDTO(
                    c.getNombre(),
                    c.getTelefono(),
                    c.getTotalComprado()))
            .toList();

}

}