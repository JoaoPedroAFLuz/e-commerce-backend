package com.joaopedroluz.cursomc.resources;

import com.joaopedroluz.cursomc.domain.Estado;
import com.joaopedroluz.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Estado> find(@PathVariable Integer id) {
        Estado obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }
}
