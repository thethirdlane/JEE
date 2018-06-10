package ttl.larku.ejb.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ttl.larku.domain.Student;
import ttl.larku.service.ejb.RegistrationFacade;

public class RegistrationApp
{
	private Context context;
	private RegistrationFacade registrationService;

    public static void main(String[] args) {  
		//String slBeanName = "java:global/2_LarkU_EJB/RegistrationFacadeImpl";
		String slBeanName = "java:global/JPA_EJB/RegistrationFacadeImpl";
		//String eeslBeanName = "java:global/3_LarkU_EE/3_LarkU_EJB_EE/RegistrationFacadeImpl";
		String eeslBeanName = "java:global/5_LarkU_EE/5_LarkU_EJB_JPA/RegistrationFacadeImpl!ttl.larku.service.ejb.RegistrationFacade";
    	RegistrationApp app = new RegistrationApp(slBeanName);
    	app.doit();
    }
    
    public void doit() {
        //StudentService studentService = registrationService.getStudentService();
        //System.out.println("ss is " + studentService);

    	List<Student> students = registrationService.getAllStudents();
    	System.out.println(students);
        //Interceptor
        //Object result = studentService.getStudent(1);
        
        //Created Event
    	/*
        Student student = new Student("Vivek");
        student = registrationService.createStudent(student);
        System.out.println("new student = " + student);
        
        registrationService.addNewClassToSchedule("Math101", "10/10/14", "10/10/15");
        List<ScheduledClass> classes = registrationService.getAllScheduledClasses();
        for(ScheduledClass sc : classes) {
        	System.out.println("Class: " + sc);
        }
        
        registrationService.registerStudentForClass(1, "Math101", "10/10/14");
        
        List<Student> students = registrationService.getStudentsForClass("Math101", "10/10/14");
        for(Student s : students) {
        	System.out.println("Student: " + s);
        }
        */
    }
    
    public RegistrationApp(String jndiName) {
    	
		try {
			context = new InitialContext();
			registrationService = (RegistrationFacade) context.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }
}
