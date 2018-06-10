package ttl.larku.soap.client;

import java.util.List;

import ttl.larku.soap.generated.RegistrationWSFacadeImpl;
import ttl.larku.soap.generated.RegistrationWSFacadeImplService;
import ttl.larku.soap.generated.Student;

public class SoapClient {

	public static void main(String[] args) {
		RegistrationWSFacadeImplService factory = new RegistrationWSFacadeImplService();
		RegistrationWSFacadeImpl service = factory
				.getRegistrationWSFacadeImplPort();
/*
		BindingProvider bp = (BindingProvider) service;
		bp.getRequestContext()
				.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
						"http://notyetmint:8888/RegistrationWSFacadeImplService/RegistrationWSFacadeImpl");
*/

		List<Student> students = service.getAllStudents();

		for (Student student : students) {
			System.out.println(student.toString());
		}
	}
}
