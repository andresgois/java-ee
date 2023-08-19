package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDAO implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;

	public boolean existe(Usuario usuario) {
        TypedQuery<Usuario> query = em.createQuery(
                "select u from Usuario u where u.email = :pEmail and u.senha = :pSenha",
                Usuario.class);
        query.setParameter("pEmail", usuario.getEmail());
        query.setParameter("pSenha", usuario.getSenha());
        
        try {
            Usuario result = query.getSingleResult();
            System.out.println(result.getEmail());
        } catch (NoResultException e) {
            return false;
        }
        return true;
    }
    
}
