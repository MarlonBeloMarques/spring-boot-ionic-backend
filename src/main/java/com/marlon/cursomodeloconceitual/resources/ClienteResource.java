package com.marlon.cursomodeloconceitual.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marlon.cursomodeloconceitual.domain.Cliente;
import com.marlon.cursomodeloconceitual.dto.ClienteDTO;
import com.marlon.cursomodeloconceitual.dto.ClienteNewDTO;
import com.marlon.cursomodeloconceitual.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes") // endereço
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { // o id acima faz referencia com o de baixo
		Cliente obj = service.find(id);	 //vai buscar o id passado
		return ResponseEntity.ok().body(obj); //retorna ok o obj
	}
	
	//Alterar
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<Void> delete(@PathVariable Integer id) { // o id acima faz referencia com o de baixo
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<List<ClienteDTO>> findAll() { // o id acima faz referencia com o de baixo
		List<Cliente> list = service.findAll();	 //vai buscar o id passado
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList()); //percorre a lista , mapea em cada objeto da lista criando um objeto dto e volta para o tipo lista 
		return ResponseEntity.ok().body(listDto); //retorna ok o obj
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET) // pegar pelo id
	// relacao com HTTP
	public ResponseEntity<Page<ClienteDTO>> findAll(
			@RequestParam(value="page", defaultValue="0")Integer page, // valor padrão 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction) { // o id acima faz referencia com o de baixo, defaultValue = (ascendente ou descendente)
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);	 //vai buscar o id passado
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj)); 
		return ResponseEntity.ok().body(listDto); //retorna ok o obj
	}
	
	//iNSERIR
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){ //@Valid= para poder prosseguir, precisa ser validado @RequestBody = faz o json ser convertido para objeto java
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); // enumerar de forma crescente o id do uri
		return ResponseEntity.created(uri).build();
	}
	
	//iNSERIR
	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file){ // @RequestParam(name="file") = reconhcer que chegou uma requisição do http 
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}
}
