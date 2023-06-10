package br.com.caelum.livraria.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

@Stateless
public class UsuarioDao {

	//private Banco banco = new Banco();
	@PersistenceContext
	private EntityManager em;

	public Usuario buscaPeloLogin(String login) {
		//return this.banco.buscaPeloNome(login);
		
		try {
			System.out.println("em "+this.em);
			/*return em.
					createQuery("select u from Usuario u where u.login like :pLogin", Usuario.class)
					.setParameter("pLogin", login)
					.getSingleResult();*/
			TypedQuery<Usuario> query = this.em.createQuery("select u from Usuario u where u.login = :pLogin", Usuario.class);
			query.setParameter("pLogin", login);
			Usuario usuario = query.getSingleResult();
			return usuario;
		} catch (Exception e) {
			System.out.println("Login não econtrado");
			return null;
		}
	}
	
}
