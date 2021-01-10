package com.diogosilva.pagamento.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.diogosilva.pagamento.data.vo.VendaVO;
import com.diogosilva.pagamento.entity.ProdutoVenda;
import com.diogosilva.pagamento.entity.Venda;
import com.diogosilva.pagamento.exception.ResourceNotFoundException;
import com.diogosilva.pagamento.repository.ProdutoVendaRepository;
import com.diogosilva.pagamento.repository.VendaRepository;

@Service
public class VendaService {

	private final VendaRepository vendaRepository;
	private final ProdutoVendaRepository produtoVendaRepository;

	@Autowired
	public VendaService(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
		this.vendaRepository = vendaRepository;
		this.produtoVendaRepository = produtoVendaRepository;
	}

	public VendaVO create(VendaVO vendaVO) {
		Venda venda = vendaRepository.save(Venda.create(vendaVO));

		List<ProdutoVenda> produtosSalvos = new ArrayList<>();
		vendaVO.getProdutos().forEach(p -> {
			ProdutoVenda produtoVenda = ProdutoVenda.create(p);
			produtoVenda.setVenda(venda);
			produtosSalvos.add(produtoVendaRepository.save(produtoVenda));
		});
		venda.setProdutos(produtosSalvos);

		return VendaVO.create(venda);
	}

	public Page<VendaVO> findAll(Pageable pageable) {
		var page = vendaRepository.findAll(pageable);
		return page.map(this::convertToVendaVO);
	}

	private VendaVO convertToVendaVO(Venda venda) {
		return VendaVO.create(venda);
	}

	public VendaVO findById(Long id) {
		var entity = vendaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return VendaVO.create(entity);
	}
}
