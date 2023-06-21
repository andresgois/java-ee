package io.github.andresgois.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.andresgois.entity.AgendamentoEmail;
import io.github.andresgois.service.AgendamentoEmailServico;

@Path("emails")
public class AgendamentoEmailController {

	@Inject
	private AgendamentoEmailServico agendamentoEmailServico;

	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response listar() {
		return Response.ok(agendamentoEmailServico.listar()).build();
	}
	
	@POST
	@Consumes(value = MediaType.APPLICATION_JSON)
	public Response inserir(AgendamentoEmail agendamentoEmail) {
		agendamentoEmailServico.inserir(agendamentoEmail);
		return Response.status(201).build();
	}
}
