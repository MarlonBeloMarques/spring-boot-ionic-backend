package com.marlon.cursomodeloconceitual.dto;

import java.io.Serializable;

import com.marlon.cursomodeloconceitual.domain.Estado;

public class EstadoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public EstadoDTO() {
		
	}
	
	public EstadoDTO(Estado obj) {
		setId(obj.getId());
		setNome(obj.getNome());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
