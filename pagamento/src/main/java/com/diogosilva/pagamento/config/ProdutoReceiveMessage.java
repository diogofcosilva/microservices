package com.diogosilva.pagamento.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.diogosilva.pagamento.data.vo.ProdutoVO;
import com.diogosilva.pagamento.entity.Produto;
import com.diogosilva.pagamento.repository.ProdutoRepository;

@Component
public class ProdutoReceiveMessage {
	
	private final ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoReceiveMessage(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	@RabbitListener(queues = {"${crud.rabbitmq.queue}"})
	public void receive(@Payload ProdutoVO produtoVO) {
		produtoRepository.save(Produto.create(produtoVO));
	}
	

}
