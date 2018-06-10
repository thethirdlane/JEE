package ttl.larku.jsf.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.jsf.data.ClassDataHolder;
import ttl.larku.jsf.util.ELResolver;
import ttl.larku.service.ClassService;
import ttl.larku.service.CourseService;
import ttl.larku.service.RegistrationService;
import ttl.larku.service.StudentService;

@ManagedBean
public class LarkUController {
	
	@Inject
	private RegistrationService regService;
	
	public List<Student> getAllStudents() {
		StudentService ss = regService.getStudentService();
		
		return ss.getAllStudents();
	}

	public List<Course> getAllCourses() {
		CourseService service = regService.getCourseService();
		List<Course> courses = service.getAllCourses();
		return courses;
	}

	public List<ScheduledClass> getAllClasses() {
		ClassService service = regService.getClassService();
		return service.getAllScheduledClasses();
	}

	public String getStudent() {
		Student student = ELResolver.findObject("student", Student.class);
		student = regService.getStudentService().getStudent(student.getId());
		
		//Have to replace the old student object with our new one
		Map<String, Object> req = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		Student oldStudent = (Student)req.get("student");
		
		req.put("student", student);
		
		return "showStudent";
	}

	public String getCourse() {
		Course course = ELResolver.findObject("course", Course.class);
		course = regService.getCourseService().getCourse(course.getId());
		
		//Have to replace the old student object with our new one
		Map<String, Object> req = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		Course oldCourse = (Course)req.get("course");
		
		req.put("course", course);
		
		return "showCourse";
	}
	
	public String addStudent() {
		Student student = ELResolver.findObject("student", Student.class);
		regService.getStudentService().createStudent(student);
		
		return "getStudents";
	}

	public String addCourse() {
		Course course= ELResolver.findObject("course", Course.class);
		regService.getCourseService().createCourse(course);
		
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
		
		ScheduledClass scheduledClass = regService.getClassService().
				createScheduledClass(cdh.getCourseCode(), cdh.getStartDate(), cdh.getEndDate());

		//add it to the request scope
		Map<String, Object> req = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		ScheduledClass oldClass = (ScheduledClass)req.get("scheduledClass");
		
		req.put("scheduledClass", scheduledClass);
		
		return "getClasses";
	}
	
	public String registerStudent() {
		ScheduledClass sc = ELResolver.findObject("scheduledClass", ScheduledClass.class);
		sc = regService.getClassService().getScheduledClass(sc.getId());

		Student student = ELResolver.findObject("student", Student.class);
		
		regService.registerStudentForClass(student.getId(), sc.getCourse().getCode(), sc.getStartDate());
		
		return "index";
	}

}
