package io.github.andresgois.job;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

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
	
	/*
	 *  ConnectionFactory para a fila
	 *  fabrica de conexao
	 */
	@Inject
	@JMSConnectionFactory("java:/jboss/DefaultJMSConnectionFactory")
	private JMSContext context;
	
	/*
	 * Fila
	 */
	@Resource(mappedName = "java:/jms/queue/EmailQueue")
	private Queue queue;
	
	
	@Schedule(hour = "*", minute = "*", second = "*/10")
	public void enviarEmail() {
		List<AgendamentoEmail> lista =  agendamentoEmailServico.listarPorNaoAgendado();
		lista.forEach(e -> {
			//agendamentoEmailServico.enviar(e);
			// envia pra fila
			context.createProducer().send(queue, e);
			agendamentoEmailServico.alterar(e);
		});
	}
}
