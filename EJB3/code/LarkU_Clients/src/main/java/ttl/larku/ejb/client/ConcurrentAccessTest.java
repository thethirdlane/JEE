package ttl.larku.ejb.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ttl.larku.ejb.conctest.ConcurrencyTester;


public class ConcurrentAccessTest {

	private Context context;
	private ConcurrencyTester bean;

	public static void main(String[] args) {
		String slBeanName = "java:global/Play_2_LarkU_EJB/ConcurrencyStatelessBean";
		String sfBeanName = "java:global/Play_2_LarkU_EJB/ConcurrencyStatefulBean";
		String singletonBeanName = "java:global/Play_2_LarkU_EJB/ConcurrencySingletonBean";
		ConcurrentAccessTest ssc = new ConcurrentAccessTest(sfBeanName);
		ssc.doit();
	}

	public void doit() {

		// Cick off 10 threads to get counts of what's in there
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			Worker w = new Worker("Worker " + i);
			es.execute(w);
		}

		es.shutdown();
		try {
			es.awaitTermination(5000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		
		//Get the final hit count, which will only be meaningful
		//for Stateful beans
		int hitCount = bean.getHitCount();
		System.out.println("Final Hit Count is " + hitCount);
	}

	public ConcurrentAccessTest(String jndiName) {
		try {
			context = new InitialContext();
			bean = (ConcurrencyTester) context.lookup(jndiName);
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
			//try {
				for (int i = 0; i < 100; i++) {
					String message = bean.getMessage();
					System.out.printf(
							"Worker %s sees message %s\n", name, message);
				}

			//} catch (InterruptedException e) {
			//}
		}
	}
}
