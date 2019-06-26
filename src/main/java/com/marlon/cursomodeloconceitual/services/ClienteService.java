package com.marlon.cursomodeloconceitual.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marlon.cursomodeloconceitual.domain.Cliente;
import com.marlon.cursomodeloconceitual.dto.ClienteDTO;
import com.marlon.cursomodeloconceitual.repositories.ClienteRepository;
import com.marlon.cursomodeloconceitual.services.exceptions.DataIntegrityException;
import com.marlon.cursomodeloconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id); // encontrar o id, se nao achar retorna null
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Cliente update(Cliente obj) { // quando o id não é nulo, ele atualiza
		Cliente newObj = find(obj.getId()); // verifica se existe
		updateData(newObj, obj); // atualiza esse dado com base no objeto como argumento
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	// paginção: mostrar uma quantidade especifica
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
	
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//a partir de um dto
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(),objDto.getEmail(), null,null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
