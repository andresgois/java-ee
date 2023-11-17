package br.com.casadocodigo.loja.websockets;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped // vai garantir que esse usuário exista por todo o ciclo de vida dessa aplicação
public class UsuariosSession {

	private List<Session> sessions =  new ArrayList<>();
	
	public void add(Session session) {
		sessions.add(session);
	}
	
	public List<Session> getUsuarios(){
		return sessions;
	}

	public void remove(Session session) {
		sessions.remove(session);
	}
}
