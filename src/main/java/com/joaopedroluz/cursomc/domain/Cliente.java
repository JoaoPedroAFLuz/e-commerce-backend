package com.joaopedroluz.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.joaopedroluz.cursomc.domain.enums.Perfil;
import com.joaopedroluz.cursomc.domain.enums.TipoCliente;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;

    @Column(unique = true)
    private String email;
    private String cpfOuCnpj;
    private Integer tipo;

    @JsonIgnore
    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "TELEFONE")
    private Set<String> telefones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = (tipo == null) ? null : tipo.getCod();
        this.senha = senha;
        addPerfil(Perfil.CLIENTE);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public TipoCliente getTipo() {
        return TipoCliente.toEnum(tipo);
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void addEndereco(Endereco... enderecos) {
        this.enderecos.addAll(Arrays.asList(enderecos));
    }

    public void addPedidos(Pedido... pedidos) {
        this.pedidos.addAll(Arrays.asList(pedidos));
    }

    public void addTelfones(String... telefones) {
        this.telefones.addAll(Arrays.asList(telefones));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
