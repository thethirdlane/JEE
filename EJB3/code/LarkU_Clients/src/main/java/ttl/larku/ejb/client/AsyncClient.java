package ttl.larku.ejb.client;

import java.util.concurrent.Future;

import javax.naming.InitialContext;

import ttl.larku.ejb.async.AsyncEjb;

public class AsyncClient {
	public static void main(String[] args) throws Exception {
		String asyncBeanName = "java:global/5_LarkU_EE/5_LarkU_EJB_JPA/AsyncEjb";

		InitialContext ctx = new InitialContext();
		AsyncEjb ejb = (AsyncEjb) ctx.lookup(asyncBeanName);
		
		Future<Double> future = ejb.longCalculation(10);
		
		Double result = future.get();
		
		System.out.println("Result is " + result);
		
	}
}
