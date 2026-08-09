package com.example.farmawell.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.farmawell.dto.dashboard.ClienteProductoDTO;
import com.example.farmawell.dto.dashboard.ProductoAfinidadDTO;
import com.example.farmawell.dto.dashboard.ProductoFavoritoDTO;
import com.example.farmawell.repository.DetalleVentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecomendacionService {

    private final DetalleVentaRepository repository;

    public List<ProductoFavoritoDTO> obtenerProductosFavoritos(){

        List<ProductoFavoritoDTO> datos =
                repository.obtenerProductosFavoritos();

        Map<String, ProductoFavoritoDTO> favoritos = new LinkedHashMap<>();

        for(ProductoFavoritoDTO dto : datos){

            favoritos.putIfAbsent(
                    dto.getTelefono(),
                    dto
            );

        }

        return new ArrayList<>(favoritos.values());

    }

     public List<ClienteProductoDTO> clientesInteresados(String codigoProducto){

        return repository.obtenerClientesPorProducto(codigoProducto);

    }

    public List<ProductoAfinidadDTO> obtenerAfinidad(String codigoProducto, int limite){

        Pageable pageable = PageRequest.of(0, limite);

        return repository
                .obtenerProductosAfines(codigoProducto, pageable)
                .stream()
                .map(p -> new ProductoAfinidadDTO(
                        p.getCodigoProducto(),
                        p.getDescripcion(),
                        p.getMarca(),
                        p.getVecesJuntos()))
                .toList();

    }

}