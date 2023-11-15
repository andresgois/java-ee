package br.com.casadocodigo.loja.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.infra.MailSender;
import br.com.casadocodigo.loja.models.Compra;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/topics/CarrinhoComprasTopico") })
public class EnviaEmailCompra implements MessageListener {

	@Inject
	private MailSender mailSender;

	@Inject
	private CompraDao compraDao;

	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;

			Compra compra = compra = compraDao.buscaPorUuid(textMessage.getText());
			String messageBody = "Sua compra foi realizada com sucesso, com o número de pedido "
					+ textMessage.getText();

			mailSender.send("compras@casacodigo.com.br", compra.getUsuario().getEmail(), "Nova Compra na CDC",
					messageBody);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
