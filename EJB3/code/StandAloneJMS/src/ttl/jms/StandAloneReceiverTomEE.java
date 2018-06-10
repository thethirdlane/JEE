package ttl.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Run this with this magic argument
-DResource/javax.jms.ConnectionFactory=connectionfactory:org.apache.activemq.ActiveMQConnectionFactory:tcp://localhost:61616
 * Will Also need to add TomEE to the classpath
 * @author whynot
 *
 */
public class StandAloneReceiverTomEE {

	private ConnectionFactory conFactory;
	private Connection connection;
	private Queue queue;

	public StandAloneReceiverTomEE() throws NamingException, JMSException {
		Properties p = new Properties();
		p.put("java.naming.factory.initial",
				"org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", "http://127.0.0.1:8080/tomee/ejb");

		InitialContext context = new InitialContext(p);

		conFactory = (ConnectionFactory) context.lookup("jms/ConnectionFactory");

		queue = (Queue) context.lookup("jms/LarkUQ");
		connection = conFactory.createConnection();

	}

	public void receiveMessageAsync() {

		try {
			Session session = connection
					.createSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageConsumer consumer = session.createConsumer(queue);
			consumer.setMessageListener(new MyReciever());
			connection.start();
			
			System.out.println("Ready to Receive");

			//System.out.println("Going to receive"); 
			//TextMessage message = (TextMessage)consumer.receive();
			//System.out.println("Message Received: " + message.getText());

			//session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receiveMessage() {

		try {
			Session session = connection
					.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			System.out.println("Going to receive"); 
			TextMessage message = (TextMessage)consumer.receive();
			System.out.println("Message Received: " + message.getText());

			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class MyReciever implements MessageListener {

		@Override
		public void onMessage(Message message) {
			TextMessage tm = (TextMessage)message;
			try {
				System.out.println("Asynchronously received " + tm.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void receiveMessage(String messageSelector) {

		try {
			Session session = connection
					.createSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageConsumer consumer = session.createConsumer(queue, messageSelector);

			connection.start();
			System.out.println("Going to receive with Selector " + messageSelector);
			TextMessage message = (TextMessage)consumer.receive();
			System.out.println("Message Received: " + message.getText());

			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		StandAloneReceiverTomEE client = new StandAloneReceiverTomEE();
		
		client.receiveMessageAsync();
		//client.receiveMessage();
		//client.receiveMessage("Specialness = true and result = 4");
		
		//System.exit(0);
	}

}
