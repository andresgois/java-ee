package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.sun.jersey.api.client.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.EnderecoDTO;
import br.com.caelum.livraria.modelo.Usuario;

@Named
@ViewScoped
public class CepBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	
	private String cep;
	private String municipio;
//http://localhost:8080/busca/index.xhtml
	@Inject
	UsuarioDao dao;

	@Inject
	FacesContext context;

	public Usuario getUsuario() {
		return usuario;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public void preencherEnderecoPorCEP(String cep) throws Exception {
		String cepNaoFormatado = cep.replace("-", "");
		System.out.println("CEP: "+cep );
		Client c = Client.create();

		String url = String.format("https://viacep.com.br/ws/%s/json/", cepNaoFormatado);
		WebResource wr = c.resource(url);

		ClientResponse response = wr.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200 && response.getStatus() != 400) {
			throw new RuntimeException("A requisição falhou. Código do erro: " + response.getStatus());
		} else if (response.getStatus() == 400) {
			throw new Exception("CEP informado não encontrado. Não é permitido uso de caracteres e/ou letras no campo CEP.");
		}

		String json = wr.get(String.class);

		Gson gson = new Gson();
		EnderecoDTO enderecoDTO = gson.fromJson(json, new TypeToken<EnderecoDTO>() {}.getType());
		setMunicipio(enderecoDTO.getLocalidade());
	}
	
}
