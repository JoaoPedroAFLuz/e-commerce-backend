package com.joaopedroluz.cursomc.services;

import com.joaopedroluz.cursomc.domain.Estado;
import com.joaopedroluz.cursomc.repositories.EstadoRepository;
import com.joaopedroluz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstadoService {
    @Autowired
    private EstadoRepository repo;

    public Estado find(Integer id) {
        Optional<Estado> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Estado.class.getName()));
    }

}


