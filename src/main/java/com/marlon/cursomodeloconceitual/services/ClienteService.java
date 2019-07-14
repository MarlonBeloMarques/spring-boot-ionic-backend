package com.marlon.cursomodeloconceitual.services;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marlon.cursomodeloconceitual.domain.Cidade;
import com.marlon.cursomodeloconceitual.domain.Cliente;
import com.marlon.cursomodeloconceitual.domain.Endereco;
import com.marlon.cursomodeloconceitual.domain.enums.Perfil;
import com.marlon.cursomodeloconceitual.domain.enums.TipoCliente;
import com.marlon.cursomodeloconceitual.dto.ClienteDTO;
import com.marlon.cursomodeloconceitual.dto.ClienteNewDTO;
import com.marlon.cursomodeloconceitual.repositories.CidadeRepository;
import com.marlon.cursomodeloconceitual.repositories.ClienteRepository;
import com.marlon.cursomodeloconceitual.repositories.EnderecoRepository;
import com.marlon.cursomodeloconceitual.security.UserSS;
import com.marlon.cursomodeloconceitual.services.exceptions.AuthorizationException;
import com.marlon.cursomodeloconceitual.services.exceptions.DataIntegrityException;
import com.marlon.cursomodeloconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service s3Service;
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		// faz a busca e verifica se não é nulo ou se não é admin e se o id é diferente do qual foi buscado
		if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Cliente obj = repo.findOne(id); // encontrar o id, se nao achar retorna null
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
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
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
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
		return new Cliente(objDto.getId(), objDto.getNome(),objDto.getEmail(), null,null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = cidadeRepository.findOne(objDto.getCidadeId());
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
		
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		return s3Service.uploadFile(multipartFile);
	}
	
}
