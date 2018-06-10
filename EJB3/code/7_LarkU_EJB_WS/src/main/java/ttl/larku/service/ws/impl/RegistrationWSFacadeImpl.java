package ttl.larku.service.ws.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import ttl.larku.domain.Student;
import ttl.larku.service.ejb.RegistrationFacade;

@Stateless
@WebService
public class RegistrationWSFacadeImpl {
		

	@EJB(beanName="RegistrationFacadeImpl")
	private RegistrationFacade registrationFacade;

	public List<Student> getAllStudents() {
		return registrationFacade.getAllStudents();
	}
}
