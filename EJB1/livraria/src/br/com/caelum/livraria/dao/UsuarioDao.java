package br.com.caelum.livraria.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import br.com.caelum.livraria.modelo.Usuario;

@Stateless
public class UsuarioDao {

    //@Inject
	//private Banco banco;// = new Banco();
    @PersistenceContext
    private EntityManager em;

	public Usuario buscaPeloLogin(String login) {
		//return this.banco.buscaPeloNome(login);
	    //return this.em.createQuery("select u from Usuario u where u.login ", Usuario.class)
	    return this.em.find(Usuario.class, login);
	}
	
}
