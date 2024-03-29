package br.com.casadocodigo.loja.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Autor {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

    private String nome;

    public Autor(){}
    
    public Autor(Integer id) {
        this.id = id;
    }
    
    /*public Autor(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		Autor other = (Autor) obj;
		return Objects.equals(id, other.id);
	}
    
    
}
