package com.example.farmawell.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.dashboard.ClienteProductoDTO;
import com.example.farmawell.dto.dashboard.ClienteSegmentadoDTO;
import com.example.farmawell.segment.SegmentoCliente;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampañaService {

    private final SegmentacionService segmentacionService;
    private final RecomendacionService recomendacionService;

    public List<ClienteSegmentadoDTO> obtenerVip() {
        return filtrarPorSegmento(SegmentoCliente.VIP);
    }

    public List<ClienteSegmentadoDTO> obtenerEnRiesgo() {
        return filtrarPorSegmento(SegmentoCliente.EN_RIESGO);
    }

    public List<ClienteSegmentadoDTO> obtenerRecuperables() {
        return filtrarPorSegmento(SegmentoCliente.RECUPERABLE);
    }

    public List<ClienteProductoDTO> obtenerPorProducto(String codigoProducto) {
        return recomendacionService.clientesInteresados(codigoProducto);
    }

    private List<ClienteSegmentadoDTO> filtrarPorSegmento(SegmentoCliente segmento) {

        return segmentacionService.obtenerSegmentacion()
                .stream()
                .filter(cliente -> cliente.getSegmentos().contains(segmento))
                .filter(cliente -> cliente.getTelefono() != null
                        && !cliente.getTelefono().isBlank())
                .toList();
    }
}
