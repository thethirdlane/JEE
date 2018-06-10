package ttl.larku.app;

import java.util.List;

import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.RegistrationService;
import ttl.larku.service.StudentService;

public class RegistrationApp
{
    /**
     * @param args
     */
    public static void main(String[] args) {  
        WeldUtil wu = WeldUtil.INSTANCE;
        
        RegistrationService registrationService = wu.getBean (RegistrationService.class);
        StudentService studentService = registrationService.getStudentService();
        System.out.println("ss is " + studentService);

        //Interceptor
        Object result = studentService.getStudent(1);
        
        //Created Event
        Student student = new Student("Vivek");
        student = studentService.createStudent(student);
        System.out.println("new student = " + student);
        
        registrationService.addNewClassToSchedule("Math101", "10/10/14", "10/10/15");
        List<ScheduledClass> classes = registrationService.getClassService().getAllScheduledClasses();
        for(ScheduledClass sc : classes) {
        	System.out.println("Class: " + sc);
        }
        
        registrationService.registerStudentForClass(1, "Math101", "10/10/14");
        
        List<Student> students = registrationService.getStudentsForClass("Math101", "10/10/14");
        for(Student s : students) {
        	System.out.println("Student: " + s);
        }

        

    }
}
