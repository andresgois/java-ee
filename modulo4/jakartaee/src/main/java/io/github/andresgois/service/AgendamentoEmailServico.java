package io.github.andresgois.service;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import io.github.andresgois.dao.AgendamentoEmailDAO;
import io.github.andresgois.entity.AgendamentoEmail;

@Stateless
public class AgendamentoEmailServico {

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
	
}
