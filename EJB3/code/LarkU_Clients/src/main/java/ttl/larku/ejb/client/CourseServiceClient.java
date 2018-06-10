package ttl.larku.ejb.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ttl.larku.domain.Course;
import ttl.larku.service.ejb.CourseService;

public class CourseServiceClient {

	private Context context;
	private CourseService service;

	public static void main(String[] args) {
		String singletonBean = "java:global/3_LarkU_EE/3_LarkU_EJB_EE/CourseServiceImpl";
		CourseServiceClient ssc = new CourseServiceClient(singletonBean);
		ssc.doit();
	}

	public void doit() {
		List<Course> students = service.getAllCourses();
		for (Course s : students) {
			System.out.println(s);
		}
		
		Course c = new Course("Math-101", "Intro to Math");
		service.createCourse(c);

		students = service.getAllCourses();
		for (Course s : students) {
			System.out.println(s);
		}

	}

	public CourseServiceClient(String jndiName) {
		try {
			context = new InitialContext();
			service = (CourseService) context.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
