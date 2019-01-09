package com.spring.operacoes;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.spring.operacoes.domain.Categoria;
import com.spring.operacoes.domain.Cidade;
import com.spring.operacoes.domain.Cliente;
import com.spring.operacoes.domain.Endereco;
import com.spring.operacoes.domain.Estado;
import com.spring.operacoes.domain.ItemPedido;
import com.spring.operacoes.domain.Pagamento;
import com.spring.operacoes.domain.PagamentoComBoleto;
import com.spring.operacoes.domain.PagamentoComCartao;
import com.spring.operacoes.domain.Pedido;
import com.spring.operacoes.domain.Produto;
import com.spring.operacoes.domain.enums.EstadoPagamento;
import com.spring.operacoes.domain.enums.Perfil;
import com.spring.operacoes.domain.enums.TipoCliente;
import com.spring.operacoes.repositories.CategoriaRepository;
import com.spring.operacoes.repositories.CidadeRepository;
import com.spring.operacoes.repositories.ClienteRepository;
import com.spring.operacoes.repositories.EnderecoRepository;
import com.spring.operacoes.repositories.EstadoRepository;
import com.spring.operacoes.repositories.ItemPedidoRepository;
import com.spring.operacoes.repositories.PagamentoRepository;
import com.spring.operacoes.repositories.PedidoRepository;
import com.spring.operacoes.repositories.ProdutoRepository;

@SpringBootApplication
public class OperacoesApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public static void main(String[] args) {
		SpringApplication.run(OperacoesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Teste Tech");
		Categoria cat4 = new Categoria(null, "AAA Tech");
		Categoria cat5 = new Categoria(null, "BBB Tech");
		Categoria cat6 = new Categoria(null, "CCC Tech");
		Categoria cat7 = new Categoria(null, "DDD Tech");

		
		Produto p1  = new Produto(null, "Computador", 2000.00);
		Produto p2  = new Produto(null, "Impressora", 800.00);
		Produto p3  = new Produto(null, "Mouse", 80.00);
		Produto p4  = new Produto(null, "Mesa Escritório", 2000.00);
		Produto p5  = new Produto(null, "Toalha", 800.00);
		Produto p6  = new Produto(null, "Colcha", 80.00);
		Produto p7  = new Produto(null, "TV", 2000.00);
		Produto p8  = new Produto(null, "Roçadeira", 800.00);
		Produto p9  = new Produto(null, "Abajur", 80.00);
		Produto p10 = new Produto(null, "Pendente", 2000.00);
		Produto p11 = new Produto(null, "Shampoo", 800.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
				
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA, pe.encode("123"));
		Cliente cli2 = new Cliente(null, "Ana Costa", "ana@gmail.com", "20276774027", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli2.addPerfil(Perfil.ADMIN);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Endereco e3 = new Endereco(null, "Avenida Floriano", "225", "Sala 800", "Centro", "38777012", cli2, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
				
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));		
	}
}
