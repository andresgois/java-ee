package br.com.caelum.livraria.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDAO {
    
    public boolean existe(Usuario usuario) {
        EntityManager em = new JPAUtil().getEntityManager();

        TypedQuery<Usuario> query = em.createQuery(
                "select u from Usuario u where u.email = :pEmail and u.senha = :pSenha",
                Usuario.class);
        query.setParameter("pEmail", usuario.getEmail());
        query.setParameter("pSenha", usuario.getSenha());
        
        try {
            Usuario result = query.getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        
        em.close();
        
        return true;
    }
    
}
