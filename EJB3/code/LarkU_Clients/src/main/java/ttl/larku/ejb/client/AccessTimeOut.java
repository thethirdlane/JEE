package ttl.larku.ejb.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ejb.ConcurrentAccessTimeoutException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ttl.larku.ejb.play.AccessTimeOuter;


public class AccessTimeOut {

	private Context context;
	private AccessTimeOuter bean;
	
	public static void main(String[] args) {
		String singBeanName = "java:global/5_LarkU_EE/5_LarkU_EJB_JPA/AccessTimeouterImpl";

		AccessTimeOut ssc = new AccessTimeOut(singBeanName);
		ssc.doit();
	}

	public void doit() {

		// Cick off 10 threads to get counts of what's in there
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 2; i++) {
			Worker w = new Worker("Worker " + i);
			es.execute(w);
		}

		es.shutdown();
		try {
			es.awaitTermination(5000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		
	}

	public AccessTimeOut(String jndiName) {
		try {
			context = new InitialContext();
			bean = (AccessTimeOuter) context.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	class Worker implements Runnable {
		private String name;

		public Worker(String name) {
			this.name = name;
		}

		public void run() {
			System.out.println("Worker " + name + " going to read");
			String message = null;
			while(true) {
				try {
					message = bean.doStuff("boo");
					break;
				}
				catch(ConcurrentAccessTimeoutException e) { System.out.println(e.getMessage()); }
			}
			System.out.println("Worker " + name + " got " + message);
		}
	}
}
