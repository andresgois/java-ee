package br.com.casadocodigo.loja.daos;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.Usuario;

public class UsuarioDao implements Serializable{

    private static final long serialVersionUID = 1L;
    
	@PersistenceContext
    private EntityManager manager;
    
    public void salvar(Usuario usuario) {
        manager.persist(usuario);
    }
    
    
}
