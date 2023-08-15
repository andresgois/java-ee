package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Autor;

public class AutorDAO {

	@Inject
	private EntityManager em; // CDI faz -> new EntityManager
	
	private DAO<Autor> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Autor>(this.em ,Autor.class);
	}
	
	public List<Autor> listaTodos() {
		return this.dao.listaTodos();
	}

	public void adiciona(Autor autor) {
		this.dao.adiciona(autor);
	}

	public void atualiza(Autor autor) {
		this.dao.atualiza(autor);
	}

	public Autor buscaPorId(Integer autorId) {
		return this.dao.buscaPorId(autorId);
	}

	public void remove(Autor autor) {
		this.dao.remove(autor);
	}
	
}
