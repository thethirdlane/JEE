package ttl.jms;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class StandAloneJMSReceiver {

	private ConnectionFactory conFactory;
	private Connection connection;
	private Queue queue;
	private JMSContext jmsContext;

	public StandAloneJMSReceiver() throws NamingException, JMSException {
	    init();
	}

	public void init() throws JMSException, NamingException {
		initConnectionWebLogic("jms/ConnectionFactory", "jms/Queue");
		//initConnectionGlassfish("jms/ConnectionFactory", "jms/Queue");
	}

	public void initDelete() throws NamingException, JMSException {
		/*
		 * //For JBoss //final Hashtable<String, String> jndiProperties = new
		 * Hashtable<>(); final Hashtable<String, String> jndiProperties = new
		 * Hashtable<String, String>(); jndiProperties.put(Context.URL_PKG_PREFIXES,
		 * "org.jboss.ejb.client.naming");
		 * jndiProperties.put("java.naming.factory.initial",
		 * "org.jboss.naming.remote.client.InitialContextFactory");
		 * jndiProperties.put("java.naming.provider.url", "remote://localhost:4447");
		 * jndiProperties.put(Context.SECURITY_PRINCIPAL, "testuser");
		 * jndiProperties.put(Context.SECURITY_CREDENTIALS, "testpassword");
		 * 
		 * InitialContext context = new InitialContext(jndiProperties);
		 * 
		 * conFactory = (ConnectionFactory) context
		 * .lookup("jms/RemoteConnectionFactory");
		 * 
		 * queue = (Queue) context.lookup("jms/queue/test"); connection =
		 * conFactory.createConnection("testuser", "testpassword");
		 */
		// For WebLogic
		Hashtable<String, String> jndiProperties = new Hashtable<>();
		jndiProperties.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
		jndiProperties.put("java.naming.provider.url", "t3://localhost:7001");
		// env.put("java.naming.security.principal","user");
		// env.put("java.naming.security.credentials","password");
		Context context = new InitialContext(jndiProperties);
		// End WebLogic

		//For Glassfish
		//InitialContext context = new InitialContext();

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

		jmsContext = conFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);

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

	public void receiveMessageAsync() {

		try {
			//Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//MessageConsumer consumer = session.createConsumer(queue);
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			consumer.setMessageListener(new MyReciever());
			connection.start();

			System.out.println("Ready to Receive");

			// System.out.println("Going to receive");
			// TextMessage message = (TextMessage)consumer.receive();
			// System.out.println("Message Received: " + message.getText());

			// session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receiveMessage() {

		try {
			//Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//MessageConsumer consumer = session.createConsumer(queue);
			//connection.start();
			jmsContext.start();
			System.out.println("Going to receive");
			//TextMessage message = (TextMessage) consumer.receive();
			TextMessage message = (TextMessage) jmsContext.createConsumer(queue).receive();
			String txt = messageToString(message);
			
			System.out.println(txt);

			//session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MyReciever implements MessageListener {

		@Override
		public void onMessage(Message message) {
			TextMessage tm = (TextMessage) message;
			try {
				System.out.println("Asynchronously received " + tm.getText());
				String txt = messageToString(tm);
			
				System.out.println(txt);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}

	public void receiveMessage(String messageSelector) {

		try {
			System.out.println("Going to receive with Selector " + messageSelector);
			TextMessage message = (TextMessage) jmsContext.createConsumer(queue).receive();
			String txt = messageToString(message);


			System.out.println(txt);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void receiveMessageOld(String messageSelector) {

		try(Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
			
			MessageConsumer consumer = session.createConsumer(queue, messageSelector);

			connection.start();
			System.out.println("Going to receive with Selector " + messageSelector);
			TextMessage message = (TextMessage) consumer.receive();
			String txt = messageToString(message);
			
			
			System.out.println(txt);

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
		StandAloneJMSReceiver client = new StandAloneJMSReceiver();

		// client.receiveMessageAsync();
		//client.receiveMessage();
		client.receiveMessage("Specialness = true and result = 4");

		// System.exit(0);
	}

}
