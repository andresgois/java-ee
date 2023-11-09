package br.com.casadocodigo.loja.service;

import javax.inject.Inject;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.infra.MailSender;
import br.com.casadocodigo.loja.models.Compra;

public class EnviaEmailCompra {

	@Inject
	private MailSender mailSender;

	@Inject
	private CompraDao compraDao;

	public void enviar(String uuid) {
		Compra compra = compraDao.buscaPorUuid(uuid);
		String messageBody = "Sua compra foi realizada com sucesso, com o número de pedido " + uuid;

		mailSender.send("compras@casacodigo.com.br", compra.getUsuario().getEmail(), "Nova Compra na CDC", messageBody);
	}
}
