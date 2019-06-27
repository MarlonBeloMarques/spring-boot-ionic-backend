package com.marlon.cursomodeloconceitual.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marlon.cursomodeloconceitual.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Transactional(readOnly=true) // não precisa ser envolvida numa transação no banco de dados
	Cliente findByEmail(String email); //automaticamente reconhece a busca pelo email
}
