package ttl.larku.service.ejb.impl;

import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.ejb.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
//@RequestScoped
@Remote(RegistrationFacade.class)
@Local(RegistrationFacadeLocal.class)
public class RegistrationFacadeImpl implements RegistrationFacade, RegistrationFacadeLocal{

	@EJB
	private CourseService courseService;
	@EJB
	private ClassService classService;

	@EJB (beanName="LocalStudentServiceImpl")
	private StudentService studentService;

	
	public RegistrationFacadeImpl() {
	}
	
	public RegistrationFacadeImpl(CourseService cs, ClassService classService) {
	}
	
	@PostConstruct
	public void init() {
		//Set up some classes
		List<Course> courses = courseService.getAllCourses();
		classService.createScheduledClass("Math-101", "10/10/13", "10/10/14");
		classService.createScheduledClass("Physics-338", "10/10/14", "10/10/15");
		
	}
	
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.RegistrationService#addNewClassToSchedule(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ScheduledClass addNewClassToSchedule(String courseCode, String startDate, String endDate) {
		ScheduledClass sClass = classService.createScheduledClass(courseCode, startDate, endDate);
		return sClass;
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.RegistrationService#registerStudentForClass(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void registerStudentForClass(int studentId, String courseCode, String startDate) {
		Student student = studentService.getStudent(studentId);
		List<ScheduledClass> classes = classService.getScheduledClassesByCourseCode(courseCode);
		for(ScheduledClass sc : classes) {
			if(sc.getStartDate().equals(startDate)) {
				sc.addStudent(student);
				student.addClass(sc);
				
				break;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.RegistrationService#dropStudentFromClass(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void dropStudentFromClass(int studentId, String courseCode, String startDate) {
		Student student = studentService.getStudent(studentId);
		List<ScheduledClass> classes = classService.getScheduledClassesByCourseCode(courseCode);
		for(ScheduledClass sc : classes) {
			if(sc.getStartDate().equals(startDate)) {
				sc.removeStudent(student);
				student.dropClass(sc);
				break;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.RegistrationService#getStudentsForClass(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Student> getStudentsForClass(String courseCode, String startDate) {
		List<ScheduledClass> classes = classService.getScheduledClassesByCourseCode(courseCode);
		for(ScheduledClass sc : classes) {
			if(sc.getStartDate().equals(startDate)) {
				return sc.getStudents();
			}
		}
		return new ArrayList<Student>();
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.RegistrationService#getScheduledClasses()
	 */
	@Override
	public List<ScheduledClass> getScheduledClasses() {
		return classService.getAllScheduledClasses();
	}


	public CourseService getCourseService() {
		return courseService;
	}


	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}


	public StudentService getStudentService() {
		return studentService;
	}


	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}


	public ClassService getClassService() {
		return classService;
	}


	public void setClassService(ClassService classService) {
		this.classService = classService;
	}

	@Override
	public Student getStudent(int id) {
		return studentService.getStudent(id);
	}

	@Override
	public Student createStudent(Student student) {
		return studentService.createStudent(student);
	}

	@Override
	public Student createStudent(String name) {
		return studentService.createStudent(name);
	}

	@Override
	public List<ScheduledClass> getAllScheduledClasses() {
		return classService.getAllScheduledClasses();
	}

	@Override
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@Override
	public ScheduledClass createScheduledClass(String courseCode,
			String startDate, String endDate) {
		return classService.createScheduledClass(courseCode, startDate, endDate);
	}

	@Override
	public ScheduledClass getScheduledClass(int id) {
		return classService.getScheduledClass(id);
	}

	@Override
	public List<Course> getAllCourses() {
		return courseService.getAllCourses();
	}

	@Override
	public Course getCourse(int id) {
		return courseService.getCourse(id);
	}

	@Override
	public Course createCourse(Course course) {
		return courseService.createCourse(course);
	}
}
