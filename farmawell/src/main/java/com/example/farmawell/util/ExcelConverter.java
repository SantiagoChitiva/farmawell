package com.example.farmawell.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExcelConverter {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String toString(String valor) {

        if (valor == null)
            return "";

        return valor.trim();
    }

    public static Integer toInteger(String valor) {

        if (valor == null || valor.isBlank())
            return null;

        return Integer.parseInt(valor.replace(".", "").trim());
    }

    public static BigDecimal toBigDecimal(String valor) {

        if (valor == null || valor.isBlank())
            return BigDecimal.ZERO;

        valor = valor.replace(".", "")
                     .replace(",", ".")
                     .trim();

        return new BigDecimal(valor);
    }

    public static LocalDate toLocalDate(String valor) {

        if (valor == null || valor.isBlank())
            return null;

        return LocalDate.parse(valor.trim(), FORMATO_FECHA);
    }

}