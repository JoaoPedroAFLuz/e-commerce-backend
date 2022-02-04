package com.joaopedroluz.cursomc.services;

import com.joaopedroluz.cursomc.domain.Categoria;
import com.joaopedroluz.cursomc.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElse(null);
    }
}


