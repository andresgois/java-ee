package br.com.caelum.livraria.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.exception.LivrariaException;
import br.com.caelum.livraria.modelo.Autor;

@Stateless
public class AutorService {

	@Inject
	private AutorDao dao;
	
	public void adicionar(Autor autor) throws LivrariaException {
		this.dao.salva(autor);
		
		// simular erro
		//throw new LivrariaException();
	}

	public List<Autor> todosAutores() {
		return this.dao.todosAutores();
	}
}
