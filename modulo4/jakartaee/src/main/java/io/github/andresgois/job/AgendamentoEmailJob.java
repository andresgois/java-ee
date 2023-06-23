package io.github.andresgois.job;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import io.github.andresgois.entity.AgendamentoEmail;
import io.github.andresgois.service.AgendamentoEmailServico;

@Stateless
public class AgendamentoEmailJob {

	@Inject
	private AgendamentoEmailServico agendamentoEmailServico;
	
	@Schedule(hour = "*", minute = "*", second = "*/10")
	public void enviarEmail() {
		List<AgendamentoEmail> lista =  agendamentoEmailServico.listarPorNaoAgendado();
		lista.forEach(e -> {
			agendamentoEmailServico.enviar(e);
			agendamentoEmailServico.alterar(e);
		});
	}
}
