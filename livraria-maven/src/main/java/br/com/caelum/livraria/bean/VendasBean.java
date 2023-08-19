package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.LivroDAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean  implements Serializable{
    
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public BarChartModel getVendasModel(){
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		
		ChartSeries vendasSeries = new ChartSeries();
		vendasSeries.setLabel("Vendas 2015");
		
		List<Venda> vendas = getVendas();
		for (Venda venda : vendas) {
			vendasSeries.set(venda.getLivro().getTitulo(),	venda.getQuantidade());
		}
		
		model.addSeries(vendasSeries);
		return model;
	}
	
	public List<Venda> getVendas() {
	    return this.em.createQuery("select v from Venda v", Venda.class).getResultList();
	}
}
