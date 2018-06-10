package ttl.larku.service.rest;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import ttl.larku.domain.Student;
import ttl.larku.service.ejb.RegistrationFacade;

@Path("")
public class RestRegistration { 

	//@EJB(beanName = "RegistrationFacadeImpl")
	private RegistrationFacade regFacade;
	
	public RestRegistration() {}
	
	public RestRegistration(RegistrationFacade rf) {
		regFacade = rf;
	}


	@GET
	@Path("/student")
	@Produces({"application/xml", "application/json"})
	public List<Student> getAllStudents() {
		List<Student> students = regFacade.getAllStudents();
		return students;
	}

	@GET
	@Path("/student/{id}")
	@Produces({"application/xml", "application/json"})
	public Student getAllStudent(@PathParam("id") int id) {
		Student student = regFacade.getStudent(id);
		return student;
	}

	@POST
	@Path("/student")
	@Produces({"application/xml", "application/json"})
	@Consumes({"application/xml", "application/json"})
	public Student addStudent(Student student) {
		Student newStudent = regFacade.createStudent(student);
		return newStudent;
	}

	@POST
	@Path("/login")
	@Produces({"application/xml", "application/json"})
	public void login(@FormParam("login") String login, @FormParam("password") String pass) {
		System.out.println("login = " + login);
	}
	
}