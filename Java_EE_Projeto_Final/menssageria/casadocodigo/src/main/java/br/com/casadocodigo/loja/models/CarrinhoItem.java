package br.com.casadocodigo.loja.models;

public class CarrinhoItem {

    private Livro livro;
    private Integer quantidade;

    public CarrinhoItem(Livro livro) {
        this.setLivro(livro);
        this.quantidade = 1;
    }

    public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLivro() == null) ? 0 : getLivro().hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CarrinhoItem other = (CarrinhoItem) obj;
        if (getLivro() == null) {
            if (other.getLivro() != null)
                return false;
        } else if (!getLivro().equals(other.getLivro()))
            return false;
        return true;
    }	

}
