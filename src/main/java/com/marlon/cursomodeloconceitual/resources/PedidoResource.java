package com.marlon.cursomodeloconceitual.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marlon.cursomodeloconceitual.domain.Categoria;
import com.marlon.cursomodeloconceitual.domain.Pedido;
import com.marlon.cursomodeloconceitual.dto.CategoriaDTO;
import com.marlon.cursomodeloconceitual.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos") // endereço
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<Pedido> find(@PathVariable Integer id) { // o id acima faz referencia com o de baixo
		Pedido obj = service.find(id);	 //vai buscar o id passado
		return ResponseEntity.ok().body(obj); //retorna ok o obj
	}
	
	//iNSERIR
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){ //@Valid= para poder prosseguir, precisa ser validado @RequestBody = faz o json ser convertido para objeto java
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); // enumerar de forma crescente o id do uri
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method=RequestMethod.GET) // pegar pelo id, aproveitando o endpoint de pedidos
	// relacao com HTTP
	public ResponseEntity<Page<Pedido>> findAll(
			@RequestParam(value="page", defaultValue="0")Integer page, // valor padrão 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="instante")String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC")String direction) { // o id acima faz referencia com o de baixo, defaultValue = (ascendente ou descendente)
		Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction);	 //vai buscar o id passado
		return ResponseEntity.ok().body(list); //retorna ok o obj
	}
		
}
