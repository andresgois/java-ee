package br.com.casadocodigo.loja.beans;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.Usuario;

@Model
public class CheckoutBean {

	@Inject
    private Usuario usuario;

    @Inject
    private CarrinhoCompras carrinhoCompras;

    @Transactional
    public void finalizar() {
    	carrinhoCompras.finalizar(usuario);
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void seetUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
