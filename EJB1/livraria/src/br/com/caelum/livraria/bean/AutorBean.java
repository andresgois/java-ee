package br.com.caelum.livraria.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;

@Model
public class AutorBean {
	
	private Autor autor = new Autor();
	@Inject
	private AutorDao dao;
	
	@PostConstruct
	void aposCriacao() {
	    System.out.println("AutorDao foi criado");
	}
	
	public Autor getAutor() {
		return autor;
	}
	
	public void cadastra() {
	    System.out.println("Antes de salvar");
	    try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	    
		this.dao.salva(autor);
		
		System.out.println("Depois de salvar");
		this.autor = new Autor();
	}
	
	public List<Autor> getAutores() {
		return this.dao.todosAutores();
	}
}
