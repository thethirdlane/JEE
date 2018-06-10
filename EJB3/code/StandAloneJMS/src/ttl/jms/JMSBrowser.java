package ttl.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSBrowser {

	private ConnectionFactory conFactory;
	private Connection connection;
	private Queue queue;

	public static void main(String[] args) throws NamingException, JMSException {
		JMSBrowser jmb = new JMSBrowser();
	}
	
	public void initGlassFish(String cFactoryName, String destName) throws NamingException, JMSException {
		
		//For GlassFish
		InitialContext context = new InitialContext();
		conFactory = (ConnectionFactory) context
				.lookup("jms/ConnectionFactory");

		queue = (Queue) context.lookup("jms/Queue");
		connection = conFactory.createConnection();
	}
	
	public static Enumeration<Message> browse(Connection connection, Queue queue, String ... msgSelector) throws JMSException {
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		Enumeration<Message> messages = browse(session, queue, msgSelector);
		return messages;
		/*
		while(messages.hasMoreElements()) {
			Message message = messages.nextElement();
			
			System.out.println(message);
		}
		*/
	}
	
	public static Enumeration<Message> browse(Session session, Queue queue, String ... msgSelector) throws JMSException {
		QueueBrowser browser = null;
		if(msgSelector.length > 0) {
			browser = session.createBrowser(queue, msgSelector[0]);
		}
		else  {
			browser = session.createBrowser(queue);
		}
		
		@SuppressWarnings("unchecked")
		Enumeration<Message> messages = (Enumeration<Message>)browser.getEnumeration();
		return messages;
	}
	
	
}

