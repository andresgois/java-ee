package io.github.andresgois.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.github.andresgois.dao.UsuarioDao;
import io.github.andresgois.dto.RelatorioDto;
import io.github.andresgois.model.Usuario;
import io.github.andresgois.util.Util;

@Stateless
public class UsuarioService {

	@Inject
	UsuarioDao dao;
	
	@Transactional
	public List<Usuario> listaUsuarios(){
		return dao.findAll();
	}
	
	@Transactional
	public List<Usuario> listaUsuariosPorEstado(String e){
		return dao.findByUserPerState(e);
	}
	
	@Transactional
	public RelatorioDto relatorio(String e){
		List<Usuario> users = dao.findByUserPerState(e);
		String relatorio = Util.CriarRelatorio(users); 
		return new RelatorioDto(relatorio);
	}
}
