package io.github.andresgois.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class AgendamentoEmailServico {
	
	@PostConstruct
	void init() {
		System.out.println("==============================");
		System.out.println("======Systema Iniciado========");
		System.out.println("==============================");
	}

	public List<String> listar(){
		return List.of("andre@email.com");
	}
}
