package com.example.farmawell.segment;

import java.math.BigDecimal;

public class SegmentacionConfig {

    public static final int COMPRAS_FRECUENTE = 10;

    public static final int COMPRAS_VIP = 20;

    public static final int DIAS_EN_RIESGO = 60;

    public static final int DIAS_PERDIDO = 180;

    public static final BigDecimal GASTO_VIP =
            new BigDecimal("1000000");

    private SegmentacionConfig() {
    }
}