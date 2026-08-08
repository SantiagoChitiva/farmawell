package com.example.farmawell.service.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.farmawell.entity.Cliente;
import com.example.farmawell.entity.Producto;
import com.example.farmawell.entity.Venta;

@Component
public class ImportCache {

    private final Map<String, Cliente> clientes = new HashMap<>();
    private final Map<String, Producto> productos = new HashMap<>();
    private final Map<String, Venta> ventas = new HashMap<>();

    public Map<String, Cliente> getClientes() {
        return clientes;
    }

    public Map<String, Producto> getProductos() {
        return productos;
    }

    public Map<String, Venta> getVentas() {
        return ventas;
    }

    public void imprimirTamanos() {
        System.out.println(
                "CACHE -> Clientes: " + clientes.size()
                + " | Productos: " + productos.size()
                + " | Ventas: " + ventas.size()
        );
    }
}