package io.github.andresgois.servlet;

//@WebServlet("emails")
public class AgendamentoEmailServlet{// extends HttpServlet {
/*
	private static final long serialVersionUID = 1L;

	@Inject
	private AgendamentoEmailServico servico;

	// http://localhost:8080/agendamento-email-0.0.1-SNAPSHOT/emails
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();

		servico.listar().forEach(r -> pw.print("Os E-mails cadastrados são: " + r.getEmail()));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		BufferedReader reader = req.getReader();
		
		String p = reader.lines().collect(Collectors.joining());
		
		String[] email = p.split(",");//reader.readLine().split(",");
		System.out.println("Email: "+email[0]);
		AgendamentoEmail agendamentoEmail = new AgendamentoEmail();
		agendamentoEmail.setEmail(email[0]);
		agendamentoEmail.setAssunto(email[1]);
		agendamentoEmail.setMensagem(email[2]);
		
		servico.inserir(agendamentoEmail);
	}*/
}
