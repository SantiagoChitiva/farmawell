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
import com.example.farmawell.projection.ProductoAfinidadProjection;
import com.example.farmawell.projection.TopProductoProjection;
import com.example.farmawell.projection.VentaCategoriaProjection;
import com.example.farmawell.projection.VentaMarcaProjection;

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

@Query("""
SELECT
    p.grupoArticulo AS categoria,
    SUM(d.subtotal) AS totalVentas
FROM DetalleVenta d
JOIN d.producto p
GROUP BY p.grupoArticulo
ORDER BY SUM(d.subtotal) DESC
""")
List<VentaCategoriaProjection> obtenerVentasPorCategoria();

@Query("""
SELECT
    p.marca AS marca,
    SUM(d.subtotal) AS totalVentas
FROM DetalleVenta d
JOIN d.producto p
GROUP BY p.marca
ORDER BY SUM(d.subtotal) DESC
""")
List<VentaMarcaProjection> obtenerVentasPorMarca();

@Query("""
SELECT
    p2.codigoTns AS codigoProducto,
    p2.descripcion AS descripcion,
    p2.marca AS marca,
    COUNT(d2) AS vecesJuntos
FROM DetalleVenta d1, DetalleVenta d2
JOIN d1.producto p1
JOIN d2.producto p2
WHERE d1.venta = d2.venta
  AND p1.codigoTns = :codigoProducto
  AND p2.codigoTns <> :codigoProducto
GROUP BY p2.codigoTns, p2.descripcion, p2.marca
ORDER BY COUNT(d2) DESC
""")
List<ProductoAfinidadProjection> obtenerProductosAfines(
        @Param("codigoProducto") String codigoProducto,
        Pageable pageable);

}