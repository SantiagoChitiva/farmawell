package com.example.farmawell.importer;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.example.farmawell.dto.excel.VentaExcelDTO;

@Service
public class ExcelImporter {

    public List<VentaExcelDTO> importar(String rutaArchivo) {

        Path ruta = Paths.get(rutaArchivo);

        if (!Files.exists(ruta)) {
            throw new IllegalArgumentException(
                    "No existe el archivo: " + ruta.toAbsolutePath()
            );
        }

        try (OPCPackage pkg = OPCPackage.open(ruta.toFile(), PackageAccess.READ)) {

            ReadOnlySharedStringsTable sst = new ReadOnlySharedStringsTable(pkg);

            XSSFReader reader = new XSSFReader(pkg);

            StylesTable styles = reader.getStylesTable();

            DataFormatter formatter = new DataFormatter();

            XSSFReader.SheetIterator sheets =
                    (XSSFReader.SheetIterator) reader.getSheetsData();

            if (!sheets.hasNext()) {
                throw new IllegalArgumentException(
                        "El Excel no contiene hojas."
                );
            }

            VentaExcelSheetHandler sheetHandler = new VentaExcelSheetHandler();

            try (InputStream sheet = sheets.next()) {

                XMLReader parser = XMLHelper.newXMLReader();

                XSSFSheetXMLHandler handler =
                        new XSSFSheetXMLHandler(
                                styles,
                                null,
                                sst,
                                sheetHandler,
                                formatter,
                                false
                        );

                parser.setContentHandler(handler);

                parser.parse(new InputSource(sheet));

            }

            return sheetHandler.getVentas();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error leyendo el archivo Excel.",
                    e
            );

        }

    }

}