package io.github.andresgois.service;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import io.github.andresgois.dao.AgendamentoEmailDAO;
import io.github.andresgois.entity.AgendamentoEmail;

@Stateless
public class AgendamentoEmailServico {

	private static final Logger LOGGER = 
			Logger.getLogger(AgendamentoEmail.class.getName());
	@Inject
	private AgendamentoEmailDAO dao;
	
	@PostConstruct
	void init() {
		System.out.println("==============================");
		System.out.println("======Systema Iniciado========");
		System.out.println("==============================");
	}
	
	public List<AgendamentoEmail> listar(){
		//AgendamentoEmailDAO dao = new AgendamentoEmailDAO();
		//return List.of("andre@email.com");
		return dao.listar();
	}
	
	public void inserir(AgendamentoEmail agendamentoEmail) {
		agendamentoEmail.setAgendado(false);
		dao.inserir(agendamentoEmail);
	}
	
	public List<AgendamentoEmail> listarPorNaoAgendado(){
		return dao.listarPorNaoAgndado();
	}
	
	public void alterar(AgendamentoEmail ag) {
		ag.setAgendado(true);
		dao.alterar(ag);
	}
	
	public void enviar(AgendamentoEmail a) {
		try {
			Thread.sleep(5000);
			LOGGER.info("O e-mail do(a) usuário(a) "+a.getEmail()+ " foi enviado!");
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		}
	}
	
}
