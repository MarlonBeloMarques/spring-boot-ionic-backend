package com.marlon.cursomodeloconceitual;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marlon.cursomodeloconceitual.domain.Categoria;
import com.marlon.cursomodeloconceitual.domain.Cidade;
import com.marlon.cursomodeloconceitual.domain.Estado;
import com.marlon.cursomodeloconceitual.domain.Produto;
import com.marlon.cursomodeloconceitual.repositories.CategoriaRepository;
import com.marlon.cursomodeloconceitual.repositories.CidadeRepository;
import com.marlon.cursomodeloconceitual.repositories.EstadoRepository;
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
		
	}

}