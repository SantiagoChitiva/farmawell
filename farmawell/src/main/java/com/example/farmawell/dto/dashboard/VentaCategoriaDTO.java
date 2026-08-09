package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal; 
import lombok.AllArgsConstructor; 
import lombok.Data; 

@Data 
@AllArgsConstructor 
public class VentaCategoriaDTO { 
    private String categoria; 
    private BigDecimal totalVentas; 
}
