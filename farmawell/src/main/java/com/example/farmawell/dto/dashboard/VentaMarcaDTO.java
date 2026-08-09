package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal; 
import lombok.AllArgsConstructor; 
import lombok.Data; 

@Data 
@AllArgsConstructor 
public class VentaMarcaDTO { 
    private String marca; 
    private BigDecimal totalVentas; 
} 
