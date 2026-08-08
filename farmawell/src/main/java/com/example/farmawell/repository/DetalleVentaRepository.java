package com.example.farmawell.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.farmawell.dto.dashboard.ClienteProductoDTO;
import com.example.farmawell.dto.dashboard.ProductoFavoritoDTO;
import com.example.farmawell.entity.DetalleVenta;
import com.example.farmawell.projection.DetalleCompraProjection;
import com.example.farmawell.projection.TopProductoProjection;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
@Query("""
SELECT
    p.descripcion AS producto,
    p.marca AS marca,
    SUM(d.cantidad) AS cantidadVendida,
    SUM(d.subtotal) AS totalVendido
FROM DetalleVenta d
JOIN d.producto p
GROUP BY p.id, p.descripcion, p.marca
ORDER BY SUM(d.subtotal) DESC
""")
List<TopProductoProjection> obtenerTopProductos(Pageable pageable);


@Query("""
        SELECT new com.example.farmawell.dto.dashboard.ProductoFavoritoDTO(
            c.nombre,
            c.telefono,
            p.codigoTns,
            p.descripcion,
            COUNT(d)
        )
        FROM DetalleVenta d
        JOIN d.venta v
        JOIN v.cliente c
        JOIN d.producto p
        GROUP BY
            c.id,
            c.nombre,
            c.telefono,
            p.codigoTns,
            p.descripcion
        ORDER BY
            c.nombre,
            COUNT(d) DESC
        """)
    List<ProductoFavoritoDTO> obtenerProductosFavoritos();


    @Query("""
SELECT new com.example.farmawell.dto.dashboard.ClienteProductoDTO(
    c.codigoTns,
    c.nombre,
    c.telefono,
    COUNT(d),
    SUM(d.cantidad)
)
FROM DetalleVenta d
JOIN d.venta v
JOIN v.cliente c
JOIN d.producto p
WHERE p.codigoTns = :codigoProducto
GROUP BY
    c.codigoTns,
    c.nombre,
    c.telefono
ORDER BY
    SUM(d.cantidad) DESC
""")
List<ClienteProductoDTO> obtenerClientesPorProducto(@Param("codigoProducto") String codigoProducto);


@Query("""
SELECT
p.codigoTns as codigoProducto,
p.descripcion as descripcion,
d.cantidad as cantidad,
d.precio as precio,
d.subtotal as subtotal
FROM DetalleVenta d
JOIN d.producto p
WHERE d.venta.numeroFactura = :factura
""")
List<DetalleCompraProjection> detalleFactura(String factura);
}
