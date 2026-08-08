package com.example.farmawell.service;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.excel.VentaExcelDTO;
import com.example.farmawell.entity.Cliente;
import com.example.farmawell.repository.ClienteRepository;
import com.example.farmawell.service.cache.ImportCache;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ImportCache cache;

    public Cliente obtenerOCrear(VentaExcelDTO dto) {

        String codigo = dto.getCodigoCliente();

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        codigo = codigo.trim();

        // 1. Buscar primero en memoria
        Cliente cliente = cache.getClientes().get(codigo);

        if (cliente != null) {
            return cliente;
        }

        // 2. Buscar en la base de datos
        cliente = clienteRepository.findByCodigoTns(codigo)
                .orElse(null);

        if (cliente == null) {

            cliente = Cliente.builder()
                    .codigoTns(codigo)
                    .nombre(dto.getNombreCliente())
                    .telefono(dto.getTelefono())
                    .ciudad(dto.getCiudad())
                    .direccion(dto.getDireccion())
                    .barrio(dto.getBarrio())
                    .clasificacion(dto.getClasificacion())
                    .activo(true)
                    .build();

            cliente = clienteRepository.save(cliente);
        }

        // 3. Guardar en caché
        cache.getClientes().put(codigo, cliente);

        return cliente;
    }

    public Cliente buscarPorCodigo(String codigo) {

        return clienteRepository.findByCodigoTns(codigo)
                .orElseThrow();
    }
}