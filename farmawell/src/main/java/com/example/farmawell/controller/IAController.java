package com.example.farmawell.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmawell.dto.ia.PreguntaDTO;
import com.example.farmawell.dto.ia.RespuestaDTO;
import com.example.farmawell.service.IAService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class IAController {

    private final IAService iaService;

    @PostMapping("/consultar")
    public RespuestaDTO consultar(@RequestBody PreguntaDTO pregunta) {

        String respuesta = iaService.consultar(pregunta.getPregunta());

        return new RespuestaDTO(respuesta);
    }
}