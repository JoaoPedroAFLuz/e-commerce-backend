package com.joaopedroluz.cursomc.resources;

import com.joaopedroluz.cursomc.domain.Cidade;
import com.joaopedroluz.cursomc.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/cidades")
public class CidadeResource {

    @Autowired
    private CidadeService service;

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> find(@PathVariable Integer id) {
        Cidade obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }
}
