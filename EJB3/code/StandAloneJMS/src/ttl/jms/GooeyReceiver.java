package ttl.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class GooeyReceiver {

	private ConnectionFactory conFactory;
	private Connection connection;
	private Queue queue;
	private Session session;
	private MessageConsumer consumer;

	public GooeyReceiver(Connection connection, Queue queue) 
			throws NamingException, JMSException {

		this.connection = connection;

		this.queue = queue;
		
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		consumer = session.createConsumer(queue);
	}

	public void start() throws JMSException {
		connection.start();
	}

	public void stop() throws JMSException {
		connection.stop();
		session.close();
		connection.close();
	}

	public void stopAsync() throws JMSException {
		consumer.setMessageListener(null);
	}

	public void startAsync(MessageListener listener) throws JMSException {
		consumer.setMessageListener(listener);

		System.out.println("GooeyReceiver Registered listener");
	}


	public Message receiveMessage() {

		try {
			System.out.println("Going to regeive");
			TextMessage message = (TextMessage) consumer.receive();
			Enumeration<String> props = message.getPropertyNames();
			System.out.println("Message Received: " + message.getText());
			// session.close();
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Message receiveMessage(String messageSelector) {

		try {
			MessageConsumer tmpConsumer = session.createConsumer(queue,
					messageSelector);

			// connection.start();
			System.out.println("Going to receive with Selector "
					+ messageSelector);
			TextMessage message = (TextMessage) tmpConsumer.receive();
			System.out.println("Message Received: " + message.getText());

			// session.close();
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Session getSession() {
		return session;
	}
	
	public Queue getQueue() {
		return queue;
	}
	
	public Connection getConnection() {
		return connection;
	}
}
