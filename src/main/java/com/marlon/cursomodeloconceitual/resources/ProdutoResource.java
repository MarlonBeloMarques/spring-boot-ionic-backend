package com.marlon.cursomodeloconceitual.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marlon.cursomodeloconceitual.domain.Categoria;
import com.marlon.cursomodeloconceitual.domain.Produto;
import com.marlon.cursomodeloconceitual.dto.CategoriaDTO;
import com.marlon.cursomodeloconceitual.dto.ProdutoDTO;
import com.marlon.cursomodeloconceitual.resources.utils.URL;
import com.marlon.cursomodeloconceitual.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos") // endereço
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<Produto> find(@PathVariable Integer id) { // o id acima faz referencia com o de baixo
		Produto obj = service.find(id);	 //vai buscar o id passado
		return ResponseEntity.ok().body(obj); //retorna ok o obj
	}
	
	@RequestMapping(method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<Page<ProdutoDTO>> findAll(
			@RequestParam(value="nome", defaultValue="")String nome, // valor padrão 
			@RequestParam(value="categorias", defaultValue="")String categorias, // valor padrão 
			@RequestParam(value="page", defaultValue="0")Integer page, // valor padrão 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction) { // o id acima faz referencia com o de baixo, defaultValue = (ascendente ou descendente)
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);	 //vai buscar o id passado
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj)); 
		return ResponseEntity.ok().body(listDto); //retorna ok o obj
	}
}
