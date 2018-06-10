package ttl.larku.service.jms.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import ttl.larku.service.ejb.RegistrationFacade;

@MessageDriven(mappedName="jms/Queue", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		 @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		})
public class NewRegistrationListener implements MessageListener {

	public NewRegistrationListener() {
	}

	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				String text = ((TextMessage) message).getText();
				System.out.println("NewRegistrationListener received " + text + ". Going for their money now.");
				
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
