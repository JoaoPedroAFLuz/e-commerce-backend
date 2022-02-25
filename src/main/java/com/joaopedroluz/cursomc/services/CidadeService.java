package com.joaopedroluz.cursomc.services;

import com.joaopedroluz.cursomc.domain.Cidade;
import com.joaopedroluz.cursomc.dto.CidadeDTO;
import com.joaopedroluz.cursomc.repositories.CidadeRepository;
import com.joaopedroluz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepository repo;

    public Cidade find(Integer id) {
        Optional<Cidade> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
    }

    public List<Cidade> findAll(Integer estadoId){
        return repo.findCidades(estadoId);
    }

}


