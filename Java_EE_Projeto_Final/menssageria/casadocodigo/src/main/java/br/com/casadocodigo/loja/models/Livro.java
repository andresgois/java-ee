package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@Entity
@Cacheable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String titulo;
	@Lob
	@Length(min = 10)
	@NotBlank
    private String descricao;
	@DecimalMin("20")
    private BigDecimal preco;
	@Min(50)
    private Integer numeroPaginas;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataPublicacao;
	//private Calendar dataPublicacao = Calendar.getInstance(); // Pegamos uma instância de Calendar
	
    @ManyToMany
    @Size(min=1)
    @NotNull
    @XmlElement(name="autor")
    @XmlElementWrapper(name="autores")
    private List<Autor> autores = new ArrayList<>();
    
    private String capaPath;
    
	public Livro() {
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Integer getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(Integer numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	@Override
	public String toString() {
		return "Livro [titulo=" + titulo + ", descricao=" + descricao + ", preco=" + preco + ", numeroPaginas="
				+ numeroPaginas + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Calendar getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Calendar dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getCapaPath() {
		return capaPath;
	}

	public void setCapaPath(String capaPath) {
		this.capaPath = capaPath;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		return Objects.equals(id, other.id);
	}
    
	
    
}
