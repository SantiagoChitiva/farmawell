package com.example.farmawell.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.farmawell.dto.dashboard.ClientePerfilDTO;
import com.example.farmawell.entity.Cliente;
import com.example.farmawell.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfilClienteService {

    private final ClienteService clienteService;
    private final VentaRepository ventaRepository;
    private final SegmentacionService segmentacionService;

    public ClientePerfilDTO obtenerPerfil(String codigo){

        Cliente cliente = clienteService.buscarPorCodigo(codigo);

        Long comprasLong = ventaRepository.countByClienteCodigoTns(codigo);

        int compras = comprasLong.intValue();

        BigDecimal total = ventaRepository.sumTotalByCliente(codigo);

        BigDecimal ticket =
                compras == 0
                ? BigDecimal.ZERO
                : total.divide(
                        BigDecimal.valueOf(compras),
                        2,
                        RoundingMode.HALF_UP);

        LocalDate ultima = ventaRepository.ultimaCompra(codigo);

        long dias = ultima == null
                ? 0
                : ChronoUnit.DAYS.between(
                        ultima,
                        LocalDate.now());

        List<String> segmentos =
                segmentacionService.segmentosCliente(codigo);

        return new ClientePerfilDTO(
                cliente.getCodigoTns(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getCiudad(),
                compras,
                total,
                ticket,
                ultima,
                dias,
                segmentos);
    }

}