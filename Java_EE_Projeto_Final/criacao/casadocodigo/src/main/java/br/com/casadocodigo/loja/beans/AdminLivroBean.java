package br.com.casadocodigo.loja.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AutorDao;
import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.Autor;
import br.com.casadocodigo.loja.models.Livro;

// CDI
@Named(value = "adminLivrosBean")
@RequestScoped
public class AdminLivroBean {

	private Livro livro= new Livro();
	@Inject
	private LivroDao dao;
	
	@Inject
	private AutorDao autorDao;
	
	private List<Integer> autoresId = new ArrayList<>(); // fazemos new para evitar NullPointerException
	
	@Transactional
	public String salvar() {
		for(Integer autorId : autoresId){
	         livro.getAutores().add(new Autor(autorId));
	        }
		dao.salvar(livro);
        System.out.println("Livro salvo com Sucesso!");
        System.out.println("Livros = "+livro);
        this.livro = new Livro();
        this.autoresId = new ArrayList<>();
        return "/livro/lista?faces-redirect=true";
    }
	
	public List<Autor> getAutores(){
		//return Arrays.asList(new Autor(1, "Paulo Silveira"), new Autor(2, "Sérgio Lopes"));
		return autorDao.listaAutores();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Integer> getAutoresId() {
		return autoresId;
	}

	public void setAutoresId(List<Integer> autoresId) {
		this.autoresId = autoresId;
	}
}
