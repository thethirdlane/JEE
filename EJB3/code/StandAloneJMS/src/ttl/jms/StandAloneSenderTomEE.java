package ttl.jms;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
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
public class StandAloneSenderTomEE {

	private ConnectionFactory conFactory;
	private Connection connection;
	private Queue queue;

	public StandAloneSenderTomEE() throws NamingException, JMSException {
		init();
	}

	public void init() throws NamingException, JMSException {
		Properties p = new Properties();
		p.put("java.naming.factory.initial",
				"org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", "http://127.0.0.1:8080/tomee/ejb");

		InitialContext context = new InitialContext(p);

		conFactory = (ConnectionFactory) context.lookup("jms/ConnectionFactory");

		queue = (Queue) context.lookup("jms/LarkUQ");
		connection = conFactory.createConnection();
	}

	public void sendMessage(String payload) {

		try {
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);

			TextMessage message = session.createTextMessage();

			for (int i = 0; i < 5; i++) { // int i = 0; i < 10;
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

	public void sendMessage(String payload, String propName, boolean propValue) {

		try {
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);

			for (int i = 0; i < 5; i++) {
				TextMessage message = session.createTextMessage();
				message.setBooleanProperty(propName, propValue);
				message.setText(payload);
				if((i % 2) == 0) {
					message.setIntProperty("result", i);
				}
				producer.send(message);
				System.out.println("Message sent: " + message);
			}
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		StandAloneSenderTomEE client = new StandAloneSenderTomEE();

		client.sendMessage("Boo");
		//client.sendMessage("New Special Message", "Specialness", true);

		client.connection.close();
		System.exit(0);
	}

}
