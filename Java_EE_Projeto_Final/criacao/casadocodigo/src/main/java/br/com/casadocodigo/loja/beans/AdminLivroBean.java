package br.com.casadocodigo.loja.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.casadocodigo.loja.models.Livro;

// CDI
@Named(value = "adminLivrosBean")
@RequestScoped
public class AdminLivroBean {

	private Livro livro= new Livro();
	
	public void salvar() {
        System.out.println("Livro salvo com Sucesso!");
    }

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
}
