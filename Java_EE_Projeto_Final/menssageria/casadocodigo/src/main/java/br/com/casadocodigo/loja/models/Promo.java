package br.com.casadocodigo.loja.models;

public class Promo {

    private String titulo;
    private Livro livro = new Livro();

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
