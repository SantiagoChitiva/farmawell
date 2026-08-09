package com.example.farmawell.dto.dashboard;

import lombok.AllArgsConstructor; 
import lombok.Data; 

@Data 
@AllArgsConstructor 
public class ClientePerdidoMesDTO { 
    private String mes; 
    private Long cantidad; 
}
