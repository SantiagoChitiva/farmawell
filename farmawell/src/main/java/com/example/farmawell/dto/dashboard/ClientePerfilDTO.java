package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ClientePerfilDTO {

    private String codigo;
    private String nombre;
    private String telefono;
    private String ciudad;

    private Integer compras;
    private BigDecimal totalGastado;
    private BigDecimal ticketPromedio;

    private LocalDate ultimaCompra;
    private Long diasSinComprar;

    private List<String> segmentos;

    public ClientePerfilDTO() {
    }

    public ClientePerfilDTO(
            String codigo,
            String nombre,
            String telefono,
            String ciudad,
            Integer compras,
            BigDecimal totalGastado,
            BigDecimal ticketPromedio,
            LocalDate ultimaCompra,
            Long diasSinComprar,
            List<String> segmentos) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.compras = compras;
        this.totalGastado = totalGastado;
        this.ticketPromedio = ticketPromedio;
        this.ultimaCompra = ultimaCompra;
        this.diasSinComprar = diasSinComprar;
        this.segmentos = segmentos;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Integer getCompras() {
        return compras;
    }

    public BigDecimal getTotalGastado() {
        return totalGastado;
    }

    public BigDecimal getTicketPromedio() {
        return ticketPromedio;
    }

    public LocalDate getUltimaCompra() {
        return ultimaCompra;
    }

    public Long getDiasSinComprar() {
        return diasSinComprar;
    }

    public List<String> getSegmentos() {
        return segmentos;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCompras(Integer compras) {
        this.compras = compras;
    }

    public void setTotalGastado(BigDecimal totalGastado) {
        this.totalGastado = totalGastado;
    }

    public void setTicketPromedio(BigDecimal ticketPromedio) {
        this.ticketPromedio = ticketPromedio;
    }

    public void setUltimaCompra(LocalDate ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    public void setDiasSinComprar(Long diasSinComprar) {
        this.diasSinComprar = diasSinComprar;
    }

    public void setSegmentos(List<String> segmentos) {
        this.segmentos = segmentos;
    }

}
