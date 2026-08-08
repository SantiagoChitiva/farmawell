package com.example.farmawell.service.cache;

import org.springframework.stereotype.Component;

import com.example.farmawell.repository.ClienteRepository;
import com.example.farmawell.repository.ProductoRepository;
import com.example.farmawell.repository.VentaRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CacheInitializer {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;

    private final ImportCache cache;

    @PostConstruct
    public void cargarCache() {

        System.out.println("==================================");
        System.out.println("Inicializando caché...");

        clienteRepository.findAll().forEach(cliente ->

                cache.getClientes().put(cliente.getCodigoTns(), cliente)

        );

        productoRepository.findAll().forEach(producto ->

                cache.getProductos().put(producto.getCodigoTns(), producto)

        );

        ventaRepository.findAll().forEach(venta ->

                cache.getVentas().put(venta.getNumeroFactura(), venta)

        );

        System.out.println("Clientes : " + cache.getClientes().size());
        System.out.println("Productos: " + cache.getProductos().size());
        System.out.println("Ventas   : " + cache.getVentas().size());

        System.out.println("Caché inicializado.");
        System.out.println("==================================");

    }

}