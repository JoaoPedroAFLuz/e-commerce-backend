package com.joaopedroluz.cursomc;

import com.joaopedroluz.cursomc.domain.Categoria;
import com.joaopedroluz.cursomc.domain.Produto;
import com.joaopedroluz.cursomc.repositories.CategoriaRepository;
import com.joaopedroluz.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    public void run(String ...args) throws Exception {
        Categoria cat1 = new Categoria("Informática");
        Categoria cat2 = new Categoria("Escritório");

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));

        Produto p1 = new Produto("Computador", 2000.00);
        Produto p2 = new Produto("Impressora", 800.00);
        Produto p3 = new Produto("Mouse", 80.00);

        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

        cat1.addProduto(p1, p3);
        cat2.addProduto(p2);

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));


    }
}
