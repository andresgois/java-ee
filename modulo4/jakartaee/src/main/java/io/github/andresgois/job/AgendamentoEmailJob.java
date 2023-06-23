package io.github.andresgois.job;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;

import io.github.andresgois.entity.AgendamentoEmail;
import io.github.andresgois.service.AgendamentoEmailServico;

//@Stateless
@Singleton
public class AgendamentoEmailJob {
	
	/*private static AgendamentoEmailJob instance;
	
	private AgendamentoEmailJob() {}
	
	public synchronized static AgendamentoEmailJob getInstance() {
		if(instance == null) {
			instance = new AgendamentoEmailJob();
		}
		return instance;
	}*/

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
