package com.example.farmawell.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.farmawell.entity.Venta;
import com.example.farmawell.projection.ClienteInactivoProjection;
import com.example.farmawell.projection.ClienteNuevoMesProjection;
import com.example.farmawell.projection.ClientePerdidoMesProjection;
import com.example.farmawell.projection.ClienteSegmentadoProjection;
import com.example.farmawell.projection.ClienteVipProjection;
import com.example.farmawell.projection.HistorialCompraProjection;
import com.example.farmawell.projection.TicketPromedioMensualProjection;
import com.example.farmawell.projection.TopClienteProjection;
import com.example.farmawell.projection.VentaCiudadProjection;
import com.example.farmawell.projection.VentaMesProjection;
import com.example.farmawell.projection.VentaSedeProjection;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    Optional<Venta> findByNumeroFactura(String numeroFactura);

    @Query("""
SELECT
    c.nombre AS nombre,
    c.telefono AS telefono,
    COUNT(v.id) AS compras,
    SUM(v.total) AS totalComprado
FROM Venta v
JOIN v.cliente c
GROUP BY c.id, c.nombre, c.telefono
ORDER BY SUM(v.total) DESC
""")
List<TopClienteProjection> obtenerTopClientes(Pageable pageable);

@Query("""
SELECT
    c.nombre AS nombre,
    c.telefono AS telefono,
    MAX(v.fecha) AS ultimaCompra
FROM Venta v
JOIN v.cliente c
GROUP BY c.id,c.nombre,c.telefono
HAVING MAX(v.fecha) <= :fechaLimite
ORDER BY MAX(v.fecha)
""")
List<ClienteInactivoProjection> obtenerClientesInactivos(
        LocalDate fechaLimite);

        @Query("""
SELECT

c.nombre as nombre,
c.telefono as telefono,
SUM(v.total) as totalComprado

FROM Venta v

JOIN v.cliente c

GROUP BY c.id,c.nombre,c.telefono

ORDER BY SUM(v.total) DESC

""")
List<ClienteVipProjection> obtenerClientesVip(Pageable pageable);

@Query("""

SELECT

c.codigoTns as codigo,

c.nombre as nombre,

c.telefono as telefono,

COUNT(v.id) as compras,

SUM(v.total) as totalComprado,

MAX(v.fecha) as ultimaCompra

FROM Venta v

JOIN v.cliente c

GROUP BY

c.id,
c.codigoTns,
c.nombre,
c.telefono

""")
List<ClienteSegmentadoProjection> obtenerSegmentacion();

Long countByClienteCodigoTns(String codigo);

@Query("""
SELECT COALESCE(SUM(v.total),0)
FROM Venta v
WHERE v.cliente.codigoTns = :codigo
""")
BigDecimal sumTotalByCliente(String codigo);

@Query("""
SELECT MAX(v.fecha)
FROM Venta v
WHERE v.cliente.codigoTns = :codigo
""")
LocalDate ultimaCompra(String codigo);

@Query("""
SELECT
v.numeroFactura as numeroFactura,
v.fecha as fecha,
COUNT(d.id) as productos,
v.subtotal as subtotal,
v.descuento as descuento,
v.iva as iva,
v.total as total
FROM DetalleVenta d
JOIN d.venta v
WHERE v.cliente.codigoTns = :codigo
GROUP BY
v.numeroFactura,
v.fecha,
v.subtotal,
v.descuento,
v.iva,
v.total
ORDER BY v.fecha DESC
""")
List<HistorialCompraProjection> historialCliente(String codigo);

@Query(value = """
SELECT
    TO_CHAR(fecha, 'YYYY-MM') AS mes,
    SUM(total) AS totalVentas,
    COUNT(*) AS cantidadFacturas
FROM ventas
GROUP BY mes
ORDER BY mes
""", nativeQuery = true)
List<VentaMesProjection> obtenerVentasPorMes();

@Query(value = """
SELECT
    sede AS sede,
    SUM(total) AS totalVentas
FROM ventas
GROUP BY sede
ORDER BY totalVentas DESC
""", nativeQuery = true)
List<VentaSedeProjection> obtenerVentasPorSede();

@Query(value = """
SELECT
    c.ciudad AS ciudad,
    SUM(v.total) AS totalVentas
FROM ventas v
JOIN clientes c ON c.id = v.cliente_id
GROUP BY c.ciudad
ORDER BY totalVentas DESC
""", nativeQuery = true)
List<VentaCiudadProjection> obtenerVentasPorCiudad();

@Query(value = """
SELECT
    TO_CHAR(fecha, 'YYYY-MM') AS mes,
    AVG(total) AS ticketPromedio
FROM ventas
GROUP BY mes
ORDER BY mes
""", nativeQuery = true)
List<TicketPromedioMensualProjection> obtenerTicketPromedioMensual();

@Query(value = """
SELECT
    TO_CHAR(primera_compra, 'YYYY-MM') AS mes,
    COUNT(*) AS cantidad
FROM (
    SELECT cliente_id, MIN(fecha) AS primera_compra
    FROM ventas
    GROUP BY cliente_id
) t
GROUP BY mes
ORDER BY mes
""", nativeQuery = true)
List<ClienteNuevoMesProjection> obtenerClientesNuevosPorMes();

@Query(value = """
SELECT
    TO_CHAR(ultima_compra, 'YYYY-MM') AS mes,
    COUNT(*) AS cantidad
FROM (
    SELECT cliente_id, MAX(fecha) AS ultima_compra
    FROM ventas
    GROUP BY cliente_id
) t
WHERE ultima_compra <= CURRENT_DATE - INTERVAL '180 days'
GROUP BY mes
ORDER BY mes
""", nativeQuery = true)
List<ClientePerdidoMesProjection> obtenerClientesPerdidosPorMes();

}