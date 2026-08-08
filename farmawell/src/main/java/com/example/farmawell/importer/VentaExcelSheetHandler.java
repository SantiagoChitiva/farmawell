package com.example.farmawell.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import com.example.farmawell.dto.excel.VentaExcelDTO;

public class VentaExcelSheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    private int currentRow = -1;
    private final Map<Integer, String> rowValues = new HashMap<>();
    private final List<VentaExcelDTO> ventas = new ArrayList<>();

    @Override
    public void startRow(int rowNum) {
        currentRow = rowNum;
        rowValues.clear();
    }

    @Override
    public void endRow(int rowNum) {
        if (currentRow == 0) {
            return; // cabecera
        }

        boolean filaVacia = rowValues.values().stream().allMatch(v -> v == null || v.isBlank());
        if (filaVacia) {
            return;
        }

        VentaExcelDTO dto = new VentaExcelDTO();

        dto.setCodigoCliente(rowValues.getOrDefault(ExcelColumns.CODIGO_CLIENTE, ""));
        dto.setNombreCliente(rowValues.getOrDefault(ExcelColumns.NOMBRE_CLIENTE, ""));
        dto.setTelefono(rowValues.getOrDefault(ExcelColumns.TELEFONO, ""));
        dto.setCiudad(rowValues.getOrDefault(ExcelColumns.CIUDAD, ""));
        dto.setDireccion(rowValues.getOrDefault(ExcelColumns.DIRECCION, ""));
        dto.setBarrio(rowValues.getOrDefault(ExcelColumns.BARRIO, ""));

        dto.setNumeroFactura(rowValues.getOrDefault(ExcelColumns.FACTURA, ""));
        dto.setSede(rowValues.getOrDefault(ExcelColumns.SEDE, ""));
        dto.setTipoVenta(rowValues.getOrDefault(ExcelColumns.TIPO_VENTA, ""));
        dto.setFormaPago(rowValues.getOrDefault(ExcelColumns.FORMA_PAGO, ""));
        dto.setFecha(rowValues.getOrDefault(ExcelColumns.FECHA, ""));

        dto.setCodigoProducto(rowValues.getOrDefault(ExcelColumns.CODIGO_PRODUCTO, ""));
        dto.setDescripcionProducto(rowValues.getOrDefault(ExcelColumns.DESCRIPCION, ""));
        dto.setGrupoArticulo(rowValues.getOrDefault(ExcelColumns.GRUPO_ARTICULO, ""));
        dto.setLinea(rowValues.getOrDefault(ExcelColumns.LINEA, ""));
        dto.setMarca(rowValues.getOrDefault(ExcelColumns.MARCA, ""));

        dto.setCantidad(rowValues.getOrDefault(ExcelColumns.CANTIDAD, ""));
        dto.setValorBase(rowValues.getOrDefault(ExcelColumns.VALOR_BASE, ""));
        dto.setIva(rowValues.getOrDefault(ExcelColumns.IVA, ""));
        dto.setDescuento(rowValues.getOrDefault(ExcelColumns.DESCUENTO, ""));
        dto.setTotal(rowValues.getOrDefault(ExcelColumns.TOTAL, ""));

        System.out.println(
        "FILA EXCEL=" + rowNum +
        " | FACTURA=[" + dto.getNumeroFactura() + "]" +
        " | CLIENTE=[" + dto.getCodigoCliente() + "]" +
        " | PRODUCTO=[" + dto.getCodigoProducto() + "]"
);

ventas.add(dto);
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        if (cellReference == null) {
            return;
        }

        int col = new CellReference(cellReference).getCol();
        rowValues.put(col, formattedValue == null ? "" : formattedValue);
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        // no-op
    }

    public List<VentaExcelDTO> getVentas() {
        return ventas;
    }
}