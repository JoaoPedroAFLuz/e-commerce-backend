package com.joaopedroluz.cursomc.resources;

import com.joaopedroluz.cursomc.domain.Cidade;
import com.joaopedroluz.cursomc.domain.Estado;
import com.joaopedroluz.cursomc.dto.CidadeDTO;
import com.joaopedroluz.cursomc.dto.EstadoDTO;
import com.joaopedroluz.cursomc.services.CidadeService;
import com.joaopedroluz.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;
    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> list = service.findAll();
        List<EstadoDTO> listDTO = list.stream().map(EstadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> find(@PathVariable Integer id) {
        Estado obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
        List<Cidade> list = cidadeService.findAll(estadoId);
        List<CidadeDTO> listDto = list.stream().map(CidadeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
