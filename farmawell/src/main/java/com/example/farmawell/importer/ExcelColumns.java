package com.example.farmawell.importer;

public final class ExcelColumns {

    private ExcelColumns(){}

    // Cliente
    public static final int NOMBRE_CLIENTE = 1;
    public static final int CODIGO_CLIENTE = 27;
    public static final int TELEFONO = 31;
    public static final int CIUDAD = 32;
    public static final int DIRECCION = 34;
    public static final int BARRIO = 44;

    // Venta
    public static final int FACTURA = 2;
    public static final int SEDE = 3;
    public static final int TIPO_VENTA = 4;
    public static final int FORMA_PAGO = 5;
    public static final int FECHA = 6;

    // Producto
    public static final int CODIGO_PRODUCTO = 10;
    public static final int DESCRIPCION = 12;
    public static final int GRUPO_ARTICULO = 14;
    public static final int LINEA = 26;
    public static final int MARCA = 42;

    // Valores
    public static final int CANTIDAD = 16;
    public static final int VALOR_BASE = 17;
    public static final int IVA = 18;
    public static final int DESCUENTO = 20;
    public static final int TOTAL = 21;

}