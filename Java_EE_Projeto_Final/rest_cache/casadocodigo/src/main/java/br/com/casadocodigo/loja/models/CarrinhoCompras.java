package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class CarrinhoCompras implements Serializable {

	private static final long serialVersionUID = 1L;
	private Set<CarrinhoItem> itens = new HashSet<>();

    public void add(CarrinhoItem item) {
        itens.add(item);
    }
    
    public List<CarrinhoItem> getItens() {
    	ArrayList<CarrinhoItem> i = new ArrayList<CarrinhoItem>(itens);
        return i;
    }
    
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (CarrinhoItem carrinhoItem : itens) {
            total = total.add(carrinhoItem.getLivro().getPreco()
                    .multiply(new BigDecimal(carrinhoItem.getQuantidade())));
        }

        return total;
    }
    
    public BigDecimal getTotal(CarrinhoItem item) {
        return item.getLivro().getPreco().multiply(
                new BigDecimal(item.getQuantidade()));
    }

	public void setItens(Set<CarrinhoItem> itens) {
		this.itens = itens;
	}

	public void remover(CarrinhoItem item) {
		this.itens.remove(item);
	}
    
    public Integer getquantidadeTotal(){
    	return itens.stream().mapToInt(item -> item.getQuantidade()).sum();
    }
    
}
