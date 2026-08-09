package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal; 
import lombok.AllArgsConstructor; 
import lombok.Data; 

@Data 
@AllArgsConstructor 
public class VentaSedeDTO { 
    private String sede; 
    private BigDecimal totalVentas; 
}
