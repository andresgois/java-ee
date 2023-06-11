package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.livraria.modelo.Autor;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AutorDao {

	//@Inject
	//private Banco banco;// = new Banco();
	@PersistenceContext
	private EntityManager em;
	
	@PostConstruct
	void metodoCallBack() {
		System.out.println("AutorDao iniciado");
	}
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void salva(Autor autor) {
		System.out.println("Antes de salvas");
		
		/*try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		
		}*/
		//banco.save(autor);
		this.em.persist(autor);
		System.out.println("Depois de salvas");
		
		// Forçando um erro
		//throw new RuntimeException("Serviço externo deu erro!");
	}
	
	public List<Autor> todosAutores() {
		//return banco.listaAutores();
		System.out.println("Todos os autores do banco");
		return this.em.createQuery("select a from Autor a", Autor.class).getResultList();
	}

	public Autor buscaPelaId(Integer autorId) {
		//Autor autor = this.banco.buscaPelaId(autorId);
		Autor autor = this.em.find(Autor.class, autorId);
		return autor;
	}
	
}
