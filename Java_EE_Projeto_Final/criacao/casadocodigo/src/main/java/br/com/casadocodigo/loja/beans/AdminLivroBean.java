package br.com.casadocodigo.loja.beans;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AutorDao;
import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.infra.FileSaver;
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
	
	@Inject
	private FacesContext context;
	
	private Part capaLivro;
	
	/*public AdminLivroBean() {
		context = FacesContext.getCurrentInstance();
	}*/
	
	
	//private List<Integer> autoresId = new ArrayList<>(); // fazemos new para evitar NullPointerException
	
	@Transactional
	public String salvar() throws IOException {
		// removido pra uso do converter
		/*for(Integer autorId : autoresId){
	         livro.getAutores().add(new Autor(autorId));
	     }*/
		
		dao.salvar(livro);
		
		FileSaver fileSaver = new FileSaver(); // Nossa nova classe
        livro.setCapaPath(fileSaver.write(capaLivro, "livros")); // Já chamamos o método write e já retornamos o path direto para o Livro

		
        System.out.println("Livro salvo com Sucesso!");
        System.out.println("Livros = "+livro);
        this.livro = new Livro();
        //this.autoresId = new ArrayList<>();
        
        // chamada do livroDao.salvar acima
        context.getExternalContext().getFlash().setKeepMessages(true); 
        // Aqui estamos ativando o FlashScope
        context.addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));
        
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

	public Part getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}

}
