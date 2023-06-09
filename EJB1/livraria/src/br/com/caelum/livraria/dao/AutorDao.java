package br.com.caelum.livraria.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import br.com.caelum.livraria.modelo.Autor;

@Stateless
public class AutorDao {

    //@Inject
	//private Banco banco;// = new Banco();
    
    @PersistenceContext
    private EntityManager banco;

	public void salva(Autor autor) {
		banco.persist(autor);
	}
	
	public List<Autor> todosAutores() {
		return banco.createQuery("select a from Autor a", Autor.class).getResultList();
	}

	public Autor buscaPelaId(Integer autorId) {
		Autor autor = this.banco.find(Autor.class,autorId);
		return autor;
	}
	
}
