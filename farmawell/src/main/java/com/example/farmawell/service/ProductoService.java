package com.example.farmawell.service;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.excel.VentaExcelDTO;
import com.example.farmawell.entity.Producto;
import com.example.farmawell.repository.ProductoRepository;
import com.example.farmawell.service.cache.ImportCache;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ImportCache cache;

    public Producto obtenerOCrear(VentaExcelDTO dto) {

        String codigo = dto.getCodigoProducto();

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        codigo = codigo.trim();

        // 1. Buscar primero en memoria
        Producto producto = cache.getProductos().get(codigo);

        if (producto != null) {
            return producto;
        }

        // 2. Buscar en la base de datos
        producto = productoRepository.findByCodigoTns(codigo)
                .orElse(null);

        if (producto == null) {

            producto = Producto.builder()
                    .codigoTns(codigo)
                    .descripcion(dto.getDescripcionProducto())
                    .marca(dto.getMarca())
                    .grupoArticulo(dto.getGrupoArticulo())
                    .linea(dto.getLinea())
                    .build();

            producto = productoRepository.save(producto);
        }

        // 3. Guardar en caché
        cache.getProductos().put(codigo, producto);

        return producto;
    }
}