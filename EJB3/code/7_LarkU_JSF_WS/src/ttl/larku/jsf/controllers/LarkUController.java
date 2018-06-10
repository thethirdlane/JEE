package ttl.larku.jsf.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.jsf.data.ClassDataHolder;
import ttl.larku.jsf.data.ClassList;
import ttl.larku.jsf.data.CourseList;
import ttl.larku.jsf.data.StudentList;
import ttl.larku.jsf.util.ELResolver;
import ttl.larku.service.ejb.RegistrationFacade;
import ttl.larku.service.exception.LarkUException;


@Named
@ViewScoped
public class LarkUController implements Serializable{
	
	@EJB
	private RegistrationFacade regService;
	private Student detailStudent;

	private List<Student> studentList;
    private List<Course> courseList;
    private List<ScheduledClass> classList;

	public LarkUController() {
		String s = "When does this get called";
	}

	public void getAllStudents() {
		studentList = regService.getAllStudents();

		int i = 10;
		/*
		StudentList sl = ELResolver.findObject("studentList", StudentList.class);
		sl.setList(studentList);
		*/
	}

    public List<Student> getStudentList() {
        return studentList;
    }


	public List<Course> getCourseList() {
		return courseList;
	}

	public void  getAllCourses() {
		courseList = regService.getAllCourses();
		/*
		CourseList l = ELResolver.findObject("courseList", CourseList.class);
		l.setList(courseList);
		*/
	}


	public void getAllClasses() {
		classList = regService.getAllScheduledClasses();
		/*
		ClassList l = ELResolver.findObject("classList", ClassList.class);
		l.setList(classList);
		*/
	}

    public List<ScheduledClass> getClassList() {
        return classList;
    }

    public void ajaxStudent(int id) {
	    detailStudent = regService.getStudent(id);
    }

	public Student getDetailStudent() {
	    return detailStudent;
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
	
	public String addStudent() throws LarkUException{
		Student student = ELResolver.findObject("student", Student.class);
		regService.createStudent(student);
		
		return "getStudents?faces-redirect=true";
	}

	public String addCourse() {
		Course course= ELResolver.findObject("course", Course.class);
		regService.createCourse(course);
		
		return "getCourses?faces-redirect=true";
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
		
		return "getClasses?faces-redirect=true";
	}
	
	public void setupRegistration() {
		studentList = regService.getAllStudents();
		//ListHolder<Student> lhs = new ListHolder<>(allStudents);
		
		classList = regService.getAllScheduledClasses();
		//ListHolder<ScheduledClass> lhc = new ListHolder<>(classes);
		//Map<String, Object> req = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		
		//StudentList sl2 = new StudentList(allStudents);
        /*-
		StudentList sl = ELResolver.findObject("studentList", StudentList.class);
		sl.setList(studentList);

		ClassList cl = ELResolver.findObject("classList", ClassList.class);
		cl.setList(classList);
		*/

		
		//return "registerStudent";
	}
	
	public String registerStudent() {
		ScheduledClass sc = ELResolver.findObject("scheduledClass", ScheduledClass.class);
		sc = regService.getScheduledClass(sc.getId());

		Student student = ELResolver.findObject("student", Student.class);
		
		regService.registerStudentForClass(student.getId(), sc.getCourse().getCode(), sc.getStartDate());
		
		return "index?faces-redirect=true";
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "index?faces-redirect=true";
	}

}
