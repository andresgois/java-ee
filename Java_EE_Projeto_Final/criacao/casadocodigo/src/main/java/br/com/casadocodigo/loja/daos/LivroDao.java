package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.Livro;

public class LivroDao {

	@PersistenceContext
    private EntityManager manager;
	
    public void salvar(Livro livro) {
        manager.persist(livro);
    }
    
    public List<Livro> listar() {
        String jpql = "select distinct(l) from Livro l"
            + " join fetch l.autores";        

        return manager.createQuery(jpql, Livro.class).getResultList();
    }

	public List<Livro> ultimosLancamentos() {
		String jpql = "select l from Livro l order by l.dataPublicacao desc";
	    return manager.createQuery(jpql, Livro.class)
	            .setMaxResults(5)
	            .getResultList();
	}
	
	public List<Livro> demaisLivros() {
        String jpql = "select l from Livro l order by l.dataPublicacao desc";
        return manager.createQuery(jpql, Livro.class)
        		.setFirstResult(6)
                .getResultList();
    }
    
}
