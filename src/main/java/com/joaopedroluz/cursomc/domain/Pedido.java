package com.joaopedroluz.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.*;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonFormat(pattern = "dd/mm/yyyy HH:mm")
    private Date instante;

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_de_entrega_id")
    private Endereco enderecoDeEntrega;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido() {

    }

    public Pedido(Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    public void addItens(ItemPedido... itens) {
        this.itens.addAll(Arrays.asList(itens));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id.equals(pedido.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
