package ttl.larku.ejb.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ttl.larku.ejb.conctest.ConcurrencyTester;
import ttl.larku.ejb.conctest.SimpleBean;


public class SimpleClient {

	private Context context;
	private SimpleBean bean;

	public static void main(String[] args) {
		String simpleBean = "java:global/Play_2_LarkU_EJB/SimpleBeanImpl";
		SimpleClient ssc = new SimpleClient(simpleBean);
		ssc.doit();
	}

	public void doit() {

		String result = bean.doStuff("Boo");
		System.out.println("Result is " + result);
	}

	public SimpleClient(String jndiName) {
		try {
			context = new InitialContext();
			bean = (SimpleBean) context.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
