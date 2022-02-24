package com.joaopedroluz.cursomc.services;

import com.joaopedroluz.cursomc.domain.*;
import com.joaopedroluz.cursomc.domain.enums.EstadoPagamento;
import com.joaopedroluz.cursomc.domain.enums.TipoCliente;
import com.joaopedroluz.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private BCryptPasswordEncoder pe;

    @Transactional
    public void instantiateTestDatabase() throws ParseException {

        Categoria cat1 = new Categoria("Informática");
        Categoria cat2 = new Categoria("Escritório");
        Categoria cat3 = new Categoria("Casa, mesa e banho");
        Categoria cat4 = new Categoria("Eletrônicos");
        Categoria cat5 = new Categoria("Jardinagem");
        Categoria cat6 = new Categoria("Decoração");
        Categoria cat7 = new Categoria("Perfumaria");

        Produto p1 = new Produto("Computador", 2000.00);
        Produto p2 = new Produto("Impressora", 800.00);
        Produto p3 = new Produto("Mouse", 80.00);
        Produto p4 = new Produto("Mesa de escritório", 300.00);
        Produto p5 = new Produto("Toalha", 50.00);
        Produto p6 = new Produto("Colcha", 200.00);
        Produto p7 = new Produto("TV true color", 1200.00);
        Produto p8 = new Produto("Roçadeira", 800.00);
        Produto p9 = new Produto("Abajour", 100.00);
        Produto p10 = new Produto("Pendente", 180.00);
        Produto p11 = new Produto("Shampoo", 90.00);

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

        cat1.addProduto(p1, p2, p3);
        cat2.addProduto(p2, p3);
        cat3.addProduto(p5, p6);
        cat4.addProduto(p1, p2, p3, p7);
        cat5.addProduto(p8);
        cat6.addProduto(p9, p10);
        cat7.addProduto(p11);

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));


        Estado est1 = new Estado("Minas Gerais");
        Estado est2 = new Estado("São Paulo");

        Cidade c1 = new Cidade("Uberlândia", est1);
        Cidade c2 = new Cidade("São Paulo", est2);
        Cidade c3 = new Cidade("Campinas", est2);

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        est1.addCidades(c1);
        est2.addCidades(c2, c3);

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente("Maria Silva", "joao.pedro.luz@hotmail.com", "36378912377", TipoCliente.PESSOAFISICA, pe.encode("123"));
        cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        clienteRepository.saveAll(List.of(cli1));

        Endereco e1 = new Endereco("Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
        Endereco e2 = new Endereco("Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        cli1.addEndereco(e1, e2);

        clienteRepository.saveAll(List.of(cli1));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(sdf.parse("10/10/2017 19:35"), cli1, e2);

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));


        pedidoRepository.save(ped1);
        pedidoRepository.save(ped2);

        Pagamento pagto1 = new PagamentoComCartao(EstadoPagamento.QUITADO, ped1, 6);
        Pagamento pagto2 = new PagamentoComBoleto(EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);

        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ped1.setPagamento(pagto1);
        ped2.setPagamento(pagto2);

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

        cli1.addPedidos(ped1, ped2);
        clienteRepository.saveAll(List.of(cli1));

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1);

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

        ped1.addItens(ip1, ip3);
        p1.addItens(ip1);
        p2.addItens(ip2);
        p3.addItens(ip3);
    }


}
