package com.example.farmawell.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.farmawell.dto.dashboard.ClienteInactivoDTO;
import com.example.farmawell.dto.dashboard.ClienteNuevoMesDTO;
import com.example.farmawell.dto.dashboard.ClientePerdidoMesDTO;
import com.example.farmawell.dto.dashboard.ClienteVipDTO;
import com.example.farmawell.dto.dashboard.DashboardResumenDTO;
import com.example.farmawell.dto.dashboard.TicketPromedioMensualDTO;
import com.example.farmawell.dto.dashboard.TopClienteDTO;
import com.example.farmawell.dto.dashboard.TopProductoDTO;
import com.example.farmawell.dto.dashboard.VentaCategoriaDTO;
import com.example.farmawell.dto.dashboard.VentaCiudadDTO;
import com.example.farmawell.dto.dashboard.VentaMarcaDTO;
import com.example.farmawell.dto.dashboard.VentaMesDTO;
import com.example.farmawell.dto.dashboard.VentaSedeDTO;
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

public List<VentaMesDTO> obtenerVentasPorMes(){

    return ventaRepository
            .obtenerVentasPorMes()
            .stream()
            .map(p -> new VentaMesDTO(
                    p.getMes(),
                    p.getTotalVentas(),
                    p.getCantidadFacturas()))
            .toList();

}

public List<VentaSedeDTO> obtenerVentasPorSede(){

    return ventaRepository
            .obtenerVentasPorSede()
            .stream()
            .map(p -> new VentaSedeDTO(
                    p.getSede(),
                    p.getTotalVentas()))
            .toList();

}

public List<VentaCiudadDTO> obtenerVentasPorCiudad(){

    return ventaRepository
            .obtenerVentasPorCiudad()
            .stream()
            .map(p -> new VentaCiudadDTO(
                    p.getCiudad(),
                    p.getTotalVentas()))
            .toList();

}

public List<VentaCategoriaDTO> obtenerVentasPorCategoria(){

    return detalleVentaRepository
            .obtenerVentasPorCategoria()
            .stream()
            .map(p -> new VentaCategoriaDTO(
                    p.getCategoria(),
                    p.getTotalVentas()))
            .toList();

}

public List<VentaMarcaDTO> obtenerVentasPorMarca(){

    return detalleVentaRepository
            .obtenerVentasPorMarca()
            .stream()
            .map(p -> new VentaMarcaDTO(
                    p.getMarca(),
                    p.getTotalVentas()))
            .toList();

}

public List<TicketPromedioMensualDTO> obtenerTicketPromedioMensual(){

    return ventaRepository
            .obtenerTicketPromedioMensual()
            .stream()
            .map(p -> new TicketPromedioMensualDTO(
                    p.getMes(),
                    p.getTicketPromedio()))
            .toList();

}

public List<ClienteNuevoMesDTO> obtenerClientesNuevosPorMes(){

    return ventaRepository
            .obtenerClientesNuevosPorMes()
            .stream()
            .map(p -> new ClienteNuevoMesDTO(
                    p.getMes(),
                    p.getCantidad()))
            .toList();

}

public List<ClientePerdidoMesDTO> obtenerClientesPerdidosPorMes(){

    return ventaRepository
            .obtenerClientesPerdidosPorMes()
            .stream()
            .map(p -> new ClientePerdidoMesDTO(
                    p.getMes(),
                    p.getCantidad()))
            .toList();

}

}