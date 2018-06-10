package ttl.larku.ejb.client;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ttl.larku.domain.Student;
import ttl.larku.service.ejb.StudentService;

public class StudentServiceClient {

	private Context context;
	private StudentService studentService;

	public static void main(String[] args) {
		String singletonBean = "java:global/3_LarkU_EJB_EE/Course";
		String slBeanName = "java:global/Play_2_LarkU_EJB/StudentServiceImpl";
		String sfBeanName = "java:global/2_LarkU_EJB/SFullStudentServiceImpl";
		String sfBeanName3 = "java:global/3_LarkU_EJB_EE/SFullStudentServiceImpl";
		String sfBeanName4 = "java:global/2_LarkU_EJB/SFullStudentServiceImpl";
		StudentServiceClient ssc = new StudentServiceClient(sfBeanName4);
		ssc.doit();
	}

	public void doit() {
		List<Student> students = studentService.getAllStudents();
		for (Student s : students) {
			System.out.println(s);
		}
		Student student = new Student("Hiroko");
		student = studentService.createStudent(student);
		students = studentService.getAllStudents();
		System.out.printf(
				"Add 1 student, now there are %d students in the list\n",
				students.size());

		// Cick off 10 threads to get counts of what's in there
		/*
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			Worker w = new Worker("Worker " + i);
			es.execute(w);
		}

		es.shutdown();
		try {
			es.awaitTermination(5000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		*/

	}

	public StudentServiceClient(String jndiName) {
		try {
			//context = initContextGlassfish();
			context = initContextWebLogic();
			studentService = (StudentService) context.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * For Glassfish. NEEDS to have glassfish Client module in Classpath
	 * 
	 * @throws NamingException
	 */
	public Context initContextGlassfish() throws NamingException{

		// For GlassFish
		InitialContext context = new InitialContext();
		return context;
	}

	/**
	 * Init context for weblogic.  Needs wlthin3client.jar in the Classpath
	 * @throws NamingException
	 */
	public Context initContextWebLogic() throws NamingException{

		// For WebLogic
		Hashtable<String, String> jndiProperties = new Hashtable<>();
		jndiProperties.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
		jndiProperties.put("java.naming.provider.url", "t3://localhost:7001");
		jndiProperties.put("java.naming.security.principal","weblogic");
		jndiProperties.put("java.naming.security.credentials","passw0rd");
		Context context = new InitialContext(jndiProperties);
		return context;
	}

	class Worker implements Runnable {
		private String name;

		public Worker(String name) {
			this.name = name;
		}

		public void run() {
			//try {
				for (int i = 0; i < 100; i++) {
					int size = studentService.getAllStudents().size();
					System.out.printf(
							"Worker %s sees %d students in the list with bean %d\n", name,
							size, studentService);
				}

			//} catch (InterruptedException e) {
			//}
		}
	}
}
