package com.diogosilva.crud.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.diogosilva.crud.data.vo.ProdutoVO;

@Component
public class ProdutoSendMessage {
	
	@Value("${crud.rabbitmq.exchange}")
	String exchange;
	
	@Value("${crud.rabbitmq.routingKey}")
	String routingKey;

	public final RabbitTemplate rabbitTemplate;

	@Autowired
	public ProdutoSendMessage(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendMessage(ProdutoVO produtoVO) {
		rabbitTemplate.convertAndSend(exchange, routingKey, produtoVO);
	}
	
}
