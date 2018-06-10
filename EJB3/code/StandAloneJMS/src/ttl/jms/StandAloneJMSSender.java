package ttl.jms;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class StandAloneJMSSender {

	private ConnectionFactory conFactory;
	private Connection connection;
	private Queue queue;
	private JMSContext jmsContext;

	public StandAloneJMSSender() throws NamingException, JMSException {
		init();
	}

	public void init() throws JMSException, NamingException {
		initConnectionWebLogic("jms/ConnectionFactory", "jms/Queue");
		//initConnectionGlassfish("jms/ConnectionFactory", "jms/Queue");

		jmsContext = conFactory.createContext(Session.AUTO_ACKNOWLEDGE);
	}
	public void initDelete() throws NamingException, JMSException {
		// For JBoss final
		/*-
		Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		jndiProperties.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProperties.put("java.naming.provider.url", "remote://localhost:4447");
		jndiProperties.put(Context.SECURITY_PRINCIPAL, "testuser");
		jndiProperties.put(Context.SECURITY_CREDENTIALS, "testpassword");
		
		InitialContext context = new InitialContext(jndiProperties);
		conFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
		
		queue = (Queue) context.lookup("jms/queue/test");
		
		connection = conFactory.createConnection("testuser", "testpassword");
		//End JBoss
		*/

		// For WebLogic
		Hashtable<String, String> jndiProperties = new Hashtable<>();
		jndiProperties.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
		jndiProperties.put("java.naming.provider.url", "t3://localhost:7001");
		jndiProperties.put("java.naming.security.principal","weblogic");
		jndiProperties.put("java.naming.security.credentials","passw0rd");
		Context context = new InitialContext(jndiProperties);
		// End WebLogic

		// For GlassFish
		// InitialContext context = new InitialContext();
		// End Glassfish

		conFactory = (ConnectionFactory) context.lookup("jms/ConnectionFactory");

		queue = (Queue) context.lookup("jms/Queue");
		connection = conFactory.createConnection();
	}
	/**
	 * For Glassfish. NEEDS to have glassfish Client module in Classpath
	 *
	 * @param cFactoryName
	 * @param destName
	 * @throws NamingException
	 * @throws JMSException
	 */
	public void initConnectionGlassfish(String cFactoryName, String destName) throws NamingException, JMSException {

		// For GlassFish
		InitialContext context = new InitialContext();
		conFactory = (ConnectionFactory) context.lookup(cFactoryName);

		queue = (Queue) context.lookup(destName);
		connection = conFactory.createConnection();
	}

	public void initConnectionWebLogic(String cFactoryName, String destName) throws NamingException, JMSException {

		// For WebLogic
		Hashtable<String, String> jndiProperties = new Hashtable<>();
		jndiProperties.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
		jndiProperties.put("java.naming.provider.url", "t3://localhost:7001");
		jndiProperties.put("java.naming.security.principal","weblogic");
		jndiProperties.put("java.naming.security.credentials","passw0rd");
		Context context = new InitialContext(jndiProperties);
		conFactory = (ConnectionFactory) context.lookup(cFactoryName);



		queue = (Queue) context.lookup(destName);
		connection = conFactory.createConnection();
	}

	/**
	 * For TomEE. NEEDS to have TomEE in Classpath Also need to start Application
	 * with this incantation: -DResource/javax.jms.ConnectionFactory=
	 * connectionfactory:org.apache.activemq
	 * .ActiveMQConnectionFactory:tcp://localhost:61616
	 *
	 * @param cFactoryName
	 * @param destName
	 * @throws NamingException
	 * @throws JMSException
	 */
	public void initConnectionTomEE(String cFactoryName, String destName) throws NamingException, JMSException {

		Properties p = new Properties();
		p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", "http://127.0.0.1:8080/tomee/ejb");

		InitialContext context = new InitialContext(p);

		conFactory = (ConnectionFactory) context.lookup(cFactoryName);

		queue = (Queue) context.lookup(destName);
		connection = conFactory.createConnection();
	}

	public void sendMessage(String payload) {

		try {
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);

			TextMessage message = session.createTextMessage();

			for (int i = 0; i < Integer.MAX_VALUE; i++) { // int i = 0; i < 10;
															// i++) {
				message.setText("Message  :" + new Date() + payload);

				producer.send(message);
				System.out.println("Message sent: " + message);

				Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
			}
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(int count, String payload, String propName, boolean propValue) {

		try {
			for (int i = 0; i < count; i++) {
				TextMessage message = jmsContext.createTextMessage();
				message.setBooleanProperty(propName, propValue);
				message.setText(payload);
				// if ((i % 2) == 0) {
				message.setIntProperty("result", 4);
				// }
                //Send using Context
                jmsContext.createProducer().send(queue, message);
				System.out.println("Message sent: " + messageToString(message));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private String messageToString(TextMessage message) throws JMSException {
			StringBuilder buffer = new StringBuilder();
			buffer.append("Message Received: ").append(message.getText());
			Enumeration<String> props = message.getPropertyNames();
			while (props.hasMoreElements()) {
				String prop = props.nextElement();
				buffer.append(" ").append(prop).append(":").append(message.getStringProperty(prop)).append(" ");
			}
			
			return buffer.toString();
		
	}

	public static void main(String[] args) throws Exception {

		StandAloneJMSSender client = new StandAloneJMSSender();

		// client.sendMessage("Boo");
		client.sendMessage(1, "New Special Message", "Specialness", true);

		client.connection.close();
		System.exit(0);

		/*
		 * int count = 5; for (int i = 0; i < count; i++) { if ((i % 2) == 0) {
		 * System.out.println(i); } }
		 */
	}

}
