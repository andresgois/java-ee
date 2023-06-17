package io.github.andresgois.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.andresgois.service.AgendamentoEmailServico;

@WebServlet("emails")
public class AgendamentoEmailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AgendamentoEmailServico servico;
	//http://localhost:8080/agendamento-email-0.0.1-SNAPSHOT/emails
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		
		servico.listar().forEach(r -> pw.print("Os E-mails cadastrados são: "+ r));
	}
	
}
