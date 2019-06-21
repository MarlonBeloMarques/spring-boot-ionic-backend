package com.marlon.cursomodeloconceitual.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marlon.cursomodeloconceitual.domain.Categoria;
import com.marlon.cursomodeloconceitual.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias") // endereço
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<?> find(@PathVariable Integer id) { // o id acima faz referencia com o de baixo
		Categoria obj = service.buscar(id);	 //vai buscar o id passado
		return ResponseEntity.ok().body(obj); //retorna ok o obj
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){ // @RequestBody = faz o json ser convertido para objeto java
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); // enumerar de forma crescente o id do uri
		return ResponseEntity.created(uri).build();
	}
}
