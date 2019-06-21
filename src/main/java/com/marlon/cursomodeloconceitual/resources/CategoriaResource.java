package com.marlon.cursomodeloconceitual.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
import com.marlon.cursomodeloconceitual.dto.CategoriaDTO;
import com.marlon.cursomodeloconceitual.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias") // endereço
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { // o id acima faz referencia com o de baixo
		Categoria obj = service.find(id);	 //vai buscar o id passado
		return ResponseEntity.ok().body(obj); //retorna ok o obj
	}
	
	//iNSERIR
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){ // @RequestBody = faz o json ser convertido para objeto java
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); // enumerar de forma crescente o id do uri
		return ResponseEntity.created(uri).build();
	}
	
	//Alterar
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<Void> delete(@PathVariable Integer id) { // o id acima faz referencia com o de baixo
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<List<CategoriaDTO>> findAll() { // o id acima faz referencia com o de baixo
		List<Categoria> list = service.findAll();	 //vai buscar o id passado
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList()); //percorre a lista , mapea em cada objeto da lista criando um objeto dto e volta para o tipo lista 
		return ResponseEntity.ok().body(listDto); //retorna ok o obj
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<Page<CategoriaDTO>> findAll(
			@RequestParam(value="page", defaultValue="0")Integer page, // valor padrão 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction) { // o id acima faz referencia com o de baixo
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);	 //vai buscar o id passado
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj)); 
		return ResponseEntity.ok().body(listDto); //retorna ok o obj
	}
}
