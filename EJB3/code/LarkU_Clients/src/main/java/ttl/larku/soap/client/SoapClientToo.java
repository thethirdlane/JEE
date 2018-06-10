package ttl.larku.soap.client;


import java.util.List;

import javax.xml.ws.WebServiceRef;

import ttl.larku.soap.generated.RegistrationWSFacadeImpl;
import ttl.larku.soap.generated.Student;

public class SoapClientToo {

	@WebServiceRef(wsdlLocation="http://notyetmint:8080/RegistrationWSFacadeImplService/RegistrationWSFacadeImpl?wsdl")
    static RegistrationWSFacadeImpl service;
	public static void main(String[] args) {

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
