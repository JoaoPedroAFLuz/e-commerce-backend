package com.joaopedroluz.cursomc.resources;

import com.joaopedroluz.cursomc.domain.Cidade;
import com.joaopedroluz.cursomc.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/cidades")
public class CidadeResource {

    @Autowired
    private CidadeService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cidade> find(@PathVariable Integer id) {
        Cidade obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }
}
