package com.joaopedroluz.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joaopedroluz.cursomc.domain.enums.EstadoPagamento;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PagamentoComBoleto extends Pagamento {
    @JsonFormat(pattern = "dd/mm/yyyy HH:mm")
    private Date dataVencimento;
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date dataPagamento;

    public PagamentoComBoleto() {
    }

    public PagamentoComBoleto(EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
        super(estado, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
