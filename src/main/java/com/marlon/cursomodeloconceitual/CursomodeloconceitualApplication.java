package com.marlon.cursomodeloconceitual;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marlon.cursomodeloconceitual.domain.Categoria;
import com.marlon.cursomodeloconceitual.domain.Produto;
import com.marlon.cursomodeloconceitual.repositories.CategoriaRepository;
import com.marlon.cursomodeloconceitual.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomodeloconceitualApplication implements CommandLineRunner {

	@Autowired // pode ser injetada
	private CategoriaRepository categoriaRepository;
	@Autowired // pode ser injetada
	private ProdutoRepository produtoRepository;
	
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
	}

}
