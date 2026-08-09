package com.example.farmawell.dto.dashboard;

import java.math.BigDecimal; 
import lombok.AllArgsConstructor; 
import lombok.Data; 

@Data 
@AllArgsConstructor 
public class TicketPromedioMensualDTO { 
    private String mes; 
    private BigDecimal ticketPromedio; 
}
