package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal; 
import lombok.AllArgsConstructor; 
import lombok.Data; 

@Data 
@AllArgsConstructor 
public class VentaCiudadDTO { 
    private String ciudad; 
    private BigDecimal totalVentas; 
} 
