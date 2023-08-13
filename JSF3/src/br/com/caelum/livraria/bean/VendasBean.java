package br.com.caelum.livraria.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@ManagedBean
@ViewScoped
public class VendasBean {

	public BarChartModel getVendasModel(){
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		
		ChartSeries vendasSeries = new ChartSeries();
		vendasSeries.setLabel("Vendas 2015");
		
		List<Venda> vendas = getVendas(1234);
		for (Venda venda : vendas) {
			vendasSeries.set(venda.getLivro().getTitulo(),	venda.getQuantidade());
		}
		
		ChartSeries vendasSeries2016 = new ChartSeries();
		vendasSeries.setLabel("Vendas 2016");
		vendas = getVendas(4563);
		for (Venda venda : vendas) {
			vendasSeries2016.set(venda.getLivro().getTitulo(),	venda.getQuantidade());
		}
		
		model.addSeries(vendasSeries);
		model.addSeries(vendasSeries2016);
		return model;
	}
	
	public List<Venda> getVendas(long seed) {
		
	    List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
	    List<Venda> vendas = new ArrayList<Venda>();

	    Random random = new Random(seed);

	    for (Livro livro : livros) {
	        Integer quantidade = random.nextInt(500);
	        vendas.add(new Venda(livro, quantidade));
	    }

	    return vendas;
	}
}
