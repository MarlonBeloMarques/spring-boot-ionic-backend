package com.marlon.cursomodeloconceitual.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marlon.cursomodeloconceitual.domain.ItemPedido;
import com.marlon.cursomodeloconceitual.domain.PagamentoComBoleto;
import com.marlon.cursomodeloconceitual.domain.Pedido;
import com.marlon.cursomodeloconceitual.domain.enums.EstadoPagamento;
import com.marlon.cursomodeloconceitual.repositories.ItemPedidoRepository;
import com.marlon.cursomodeloconceitual.repositories.PagamentoRepository;
import com.marlon.cursomodeloconceitual.repositories.PedidoRepository;
import com.marlon.cursomodeloconceitual.repositories.ProdutoRepository;
import com.marlon.cursomodeloconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id); // encontrar o id, se nao achar retorna null
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		return obj;
	}
}
