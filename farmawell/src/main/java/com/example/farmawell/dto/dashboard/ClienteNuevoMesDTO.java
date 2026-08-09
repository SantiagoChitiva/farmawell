package com.example.farmawell.dto.dashboard;

import lombok.AllArgsConstructor; 
import lombok.Data; 

@Data 
@AllArgsConstructor 
public class ClienteNuevoMesDTO { 
    private String mes; 
    private Long cantidad; 
} 