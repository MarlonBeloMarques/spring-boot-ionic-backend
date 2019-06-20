package com.marlon.cursomodeloconceitual;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marlon.cursomodeloconceitual.domain.Categoria;
import com.marlon.cursomodeloconceitual.domain.Cidade;
import com.marlon.cursomodeloconceitual.domain.Cliente;
import com.marlon.cursomodeloconceitual.domain.Endereco;
import com.marlon.cursomodeloconceitual.domain.Estado;
import com.marlon.cursomodeloconceitual.domain.Pagamento;
import com.marlon.cursomodeloconceitual.domain.PagamentoComBoleto;
import com.marlon.cursomodeloconceitual.domain.PagamentoComCartao;
import com.marlon.cursomodeloconceitual.domain.Pedido;
import com.marlon.cursomodeloconceitual.domain.Produto;
import com.marlon.cursomodeloconceitual.domain.enums.EstadoPagamento;
import com.marlon.cursomodeloconceitual.domain.enums.TipoCliente;
import com.marlon.cursomodeloconceitual.repositories.CategoriaRepository;
import com.marlon.cursomodeloconceitual.repositories.CidadeRepository;
import com.marlon.cursomodeloconceitual.repositories.ClienteRepository;
import com.marlon.cursomodeloconceitual.repositories.EnderecoRepository;
import com.marlon.cursomodeloconceitual.repositories.EstadoRepository;
import com.marlon.cursomodeloconceitual.repositories.PagamentoRepository;
import com.marlon.cursomodeloconceitual.repositories.PedidoRepository;
import com.marlon.cursomodeloconceitual.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomodeloconceitualApplication implements CommandLineRunner {

	@Autowired // pode ser injetada
	private CategoriaRepository categoriaRepository;
	@Autowired // pode ser injetada
	private ProdutoRepository produtoRepository;
	@Autowired // pode ser injetada
	private EstadoRepository estadoRepository;
	@Autowired // pode ser injetada
	private CidadeRepository cidadeRepository;
	@Autowired // pode ser injetada
	private ClienteRepository clienteRepository;
	@Autowired // pode ser injetada
	private EnderecoRepository enderecoRepository;
	@Autowired // pode ser injetada
	private PagamentoRepository pagamentoRepository;
	@Autowired // pode ser injetada
	private PedidoRepository pedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomodeloconceitualApplication.class, args);
	}

	@Override // metodo de injeção
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 200.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.save(Arrays.asList(cat1,cat2)); // salva um array de categorias
		produtoRepository.save(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.save(Arrays.asList(est1,est2));
		cidadeRepository.save(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:32"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.save(Arrays.asList(ped1,ped2));
		pagamentoRepository.save(Arrays.asList(pagto1,pagto2));
		
	}

}
