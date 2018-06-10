package ttl.larku.jsf.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.jsf.data.ClassDataHolder;
import ttl.larku.jsf.util.ELResolver;
import ttl.larku.service.ejb.RegistrationFacade;

@ManagedBean
public class LarkUController {
	
	@EJB(beanInterface=RegistrationFacade.class, beanName="RegistrationFacadeImpl")
	private RegistrationFacade regService;
	
	public List<Student> getAllStudents() {
		List<Student> students = regService.getAllStudents();
		return students;
	}

	public List<Course> getAllCourses() {
		List<Course> courses = regService.getAllCourses();
		return courses;
	}

	public List<ScheduledClass> getAllClasses() {
		return regService.getAllScheduledClasses();
	}

	public String getStudent() {
		Student student = ELResolver.findObject("student", Student.class);
		student = regService.getStudent(student.getId());
		
		//Have to replace the old student object with our new one
		Map<String, Object> req = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		Student oldStudent = (Student)req.get("student");
		
		req.put("student", student);
		
		return "showStudent";
	}

	public String getCourse() {
		Course course = ELResolver.findObject("course", Course.class);
		course = regService.getCourse(course.getId());
		
		//Have to replace the old student object with our new one
		Map<String, Object> req = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		Course oldCourse = (Course)req.get("course");
		
		req.put("course", course);
		
		return "showCourse";
	}
	
	public String addStudent() {
		Student student = ELResolver.findObject("student", Student.class);
		regService.createStudent(student);
		
		return "getStudents";
	}

	public String addCourse() {
		Course course= ELResolver.findObject("course", Course.class);
		regService.createCourse(course);
		
		return "getCourses";
	}
	
	public List<SelectItem> getStatusList() {
        List<SelectItem> result = new ArrayList<SelectItem> ();
        for (Student.Status selection : Student.Status.values ())
            result.add (new SelectItem (selection));
        
        return result;
	}

	public List<SelectItem> getCreditList() {
        List<SelectItem> result = new ArrayList<SelectItem> ();
        for (Student.Status selection : Student.Status.values ())
            result.add (new SelectItem (selection));
        
        return result;
	}
	
	public String addClass() {
		ClassDataHolder cdh = ELResolver.findObject("classDataHolder", ClassDataHolder.class);
		
		ScheduledClass scheduledClass = regService.
				createScheduledClass(cdh.getCourseCode(), cdh.getStartDate(), cdh.getEndDate());

		//add it to the request scope
		Map<String, Object> req = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		ScheduledClass oldClass = (ScheduledClass)req.get("scheduledClass");
		
		req.put("scheduledClass", scheduledClass);
		
		return "getClasses";
	}
	
	public String registerStudent() {
		ScheduledClass sc = ELResolver.findObject("scheduledClass", ScheduledClass.class);
		sc = regService.getScheduledClass(sc.getId());

		Student student = ELResolver.findObject("student", Student.class);
		
		regService.registerStudentForClass(student.getId(), sc.getCourse().getCode(), sc.getStartDate());
		
		return "index";
	}

}
