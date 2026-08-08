package com.example.farmawell.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.dashboard.ClienteSegmentadoDTO;
import com.example.farmawell.projection.ClienteSegmentadoProjection;
import com.example.farmawell.repository.VentaRepository;
import com.example.farmawell.segment.SegmentacionConfig;
import com.example.farmawell.segment.SegmentoCliente;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SegmentacionService {

    private final VentaRepository ventaRepository;

    public List<ClienteSegmentadoDTO> obtenerSegmentacion() {

        return ventaRepository
                .obtenerSegmentacion()
                .stream()
                .map(this::segmentar)
                .toList();
    }

    public List<String> segmentosCliente(String codigo) {

    ClienteSegmentadoDTO cliente = obtenerSegmentacion()
            .stream()
            .filter(c -> c.getCodigo().equals(codigo))
            .findFirst()
            .orElse(null);

    if (cliente == null) {
        return List.of();
    }

    return cliente.getSegmentos()
            .stream()
            .map(Enum::name)
            .toList();
}

    private ClienteSegmentadoDTO segmentar(ClienteSegmentadoProjection p) {

        long diasSinComprar = ChronoUnit.DAYS.between(
                p.getUltimaCompra(),
                LocalDate.now());

        List<SegmentoCliente> segmentos = new ArrayList<>();

        // ============================
        // PERDIDO / EN RIESGO
        // ============================

        if (diasSinComprar > SegmentacionConfig.DIAS_PERDIDO) {

            segmentos.add(SegmentoCliente.PERDIDO);

        } else if (diasSinComprar > SegmentacionConfig.DIAS_EN_RIESGO) {

            segmentos.add(SegmentoCliente.EN_RIESGO);

        }

        // ============================
        // FRECUENTE
        // ============================

        if (p.getCompras() >= SegmentacionConfig.COMPRAS_FRECUENTE) {

            segmentos.add(SegmentoCliente.FRECUENTE);

        }

        // ============================
        // ALTO GASTO
        // ============================

        if (p.getTotalComprado() != null
                && p.getTotalComprado()
                        .compareTo(SegmentacionConfig.GASTO_VIP) >= 0) {

            segmentos.add(SegmentoCliente.ALTO_GASTO);

        }

        // ============================
        // VIP
        // ============================

        if (p.getCompras() >= SegmentacionConfig.COMPRAS_VIP
                && p.getTotalComprado() != null
                && p.getTotalComprado()
                        .compareTo(SegmentacionConfig.GASTO_VIP) >= 0
                && diasSinComprar <= SegmentacionConfig.DIAS_EN_RIESGO) {

            segmentos.add(SegmentoCliente.VIP);

        }

        // ============================
        // RECUPERABLE
        // ============================

        boolean estaPerdido = segmentos.contains(SegmentoCliente.PERDIDO);
        boolean fueFrecuente = segmentos.contains(SegmentoCliente.FRECUENTE);
        boolean fueAltoGasto = segmentos.contains(SegmentoCliente.ALTO_GASTO);

        if (estaPerdido && (fueFrecuente || fueAltoGasto)) {

            segmentos.add(SegmentoCliente.RECUPERABLE);

        }

        // ============================
        // NUEVO
        // ============================

        if (segmentos.isEmpty()) {

            segmentos.add(SegmentoCliente.NUEVO);

        }

        return ClienteSegmentadoDTO.builder()
                .codigo(p.getCodigo())
                .nombre(p.getNombre())
                .telefono(p.getTelefono())
                .compras(p.getCompras())
                .totalComprado(p.getTotalComprado())
                .ultimaCompra(p.getUltimaCompra())
                .segmentos(segmentos)
                .build();
    }

}