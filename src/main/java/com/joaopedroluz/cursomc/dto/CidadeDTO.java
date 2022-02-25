package com.joaopedroluz.cursomc.dto;

import com.joaopedroluz.cursomc.domain.Cidade;
import com.joaopedroluz.cursomc.domain.Estado;

import javax.validation.constraints.NotEmpty;

public class CidadeDTO {

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String nome;

    public CidadeDTO() {
    }

    public CidadeDTO(Cidade obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
