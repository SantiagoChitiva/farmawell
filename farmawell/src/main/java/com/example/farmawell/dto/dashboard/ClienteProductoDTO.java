package com.example.farmawell.dto.dashboard;

public class ClienteProductoDTO {

    private String codigoCliente;
    private String nombreCliente;
    private String telefono;
    private Long vecesComprado;
    private Long cantidadTotal;

    public ClienteProductoDTO(
            String codigoCliente,
            String nombreCliente,
            String telefono,
            Long vecesComprado,
            Long cantidadTotal) {

        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.telefono = telefono;
        this.vecesComprado = vecesComprado;
        this.cantidadTotal = cantidadTotal;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public Long getVecesComprado() {
        return vecesComprado;
    }

    public Long getCantidadTotal() {
        return cantidadTotal;
    }
    
    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setVecesComprado(Long vecesComprado) {
        this.vecesComprado = vecesComprado;
    }

    public void setCantidadTotal(Long cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }
}