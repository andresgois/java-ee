package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.caelum.livraria.modelo.Autor;

@Stateless
public class AutorDao {

	@Inject
	private Banco banco;// = new Banco();
	
	@PostConstruct
	void metodoCallBack() {
		System.out.println("AutorDao iniciado");
	}

	public void salva(Autor autor) {
		System.out.println("Antes de salvas");
		
		/*try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		banco.save(autor);
		System.out.println("Depois de salvas");
	}
	
	public List<Autor> todosAutores() {
		return banco.listaAutores();
	}

	public Autor buscaPelaId(Integer autorId) {
		Autor autor = this.banco.buscaPelaId(autorId);
		return autor;
	}
	
}
