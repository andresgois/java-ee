package br.com.caelum.livraria.interceptador;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LogInterceptador {

	@AroundInvoke
	public Object interceptar(InvocationContext ctx) throws Exception {
		
		long millis = System.currentTimeMillis();
		Object o = ctx.proceed();
		
		String metodo = ctx.getMethod().getName();
		String classe = ctx.getTarget().getClass().getSimpleName();
		
		System.out.println("Classe: "+classe+" | Método: "+metodo);
		System.out.println("Tempo gasto: "+(System.currentTimeMillis() - millis));
		
		return o;
	}
}
