package ttl.larku.service.ejb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import ttl.cdi.events.Created;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.ejb.ClassService;
import ttl.larku.service.ejb.CourseService;
import ttl.larku.service.ejb.RegistrationFacade;
import ttl.larku.service.ejb.RegistrationFacadeLocal;
import ttl.larku.service.ejb.StudentService;
import ttl.larku.service.ejb.interceptor.SecurityInterceptor;
import ttl.larku.service.exception.LarkUException;
import ttl.larku.service.jms.JMSSender;

@Singleton
@Remote(RegistrationFacade.class)
@Local(RegistrationFacadeLocal.class)
@DeclareRoles({"user", "admin"})
public class RegistrationFacadeImpl implements RegistrationFacade,
		RegistrationFacadeLocal {

	@Resource
	private SessionContext sessionContext;

	@EJB(beanName = "CourseServiceImpl")
	private CourseService courseService;

	@EJB(beanName = "ClassServiceImpl")
	private ClassService classService;

	@EJB(beanName = "LocalStudentServiceImpl")
	private StudentService studentService;

	@Resource(mappedName = "jms/ConnectionFactory")
	private ConnectionFactory conFactory;

	@Resource(mappedName = "jms/Queue")
	private Queue queue;

	@Inject
	@Created
	Event<Student> createdEvent;

	public RegistrationFacadeImpl() {
	}
	
	// @Inject
	public RegistrationFacadeImpl(CourseService cs, ClassService classService) {
		courseService = cs;
		// studentService = ss;
		this.classService = classService;
	}

	@PostConstruct
	public void init() {
		// Set up some classes
		List<Course> courses = courseService.getAllCourses();
		classService.createScheduledClass("Math-101", "10/10/13", "10/10/14");
		classService
				.createScheduledClass("Physics-338", "10/10/14", "10/10/15");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ttl.larku.service.ejb.impl.RegistrationService#addNewClassToSchedule(
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ScheduledClass addNewClassToSchedule(String courseCode,
			String startDate, String endDate) {
		ScheduledClass sClass = classService.createScheduledClass(courseCode,
				startDate, endDate);
		return sClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ttl.larku.service.ejb.impl.RegistrationService#registerStudentForClass
	 * (int, java.lang.String, java.lang.String)
	 */
	@Override
	public void registerStudentForClass(int studentId, String courseCode,
			String startDate) {
		Student student = studentService.getStudent(studentId);
		List<ScheduledClass> classes = classService
				.getScheduledClassesByCourseCode(courseCode);
		ScheduledClass found = null;
		for (ScheduledClass sc : classes) {
			if (sc.getStartDate().equals(startDate)) {
				sc.addStudent(student);
				student.addClass(sc);
				found = sc;
				classService.updateScheduledClass(sc);
				studentService.updateStudent(student);
				break;
			}
		}

		if (found != null) {
			String message = "New registration for student "
					+ student.getName() + " for Course "
					+ found.getCourse().getTitle() + " starting on "
					+ found.getStartDate();
			// send a Message to the Queue about a new registration
			JMSSender.sendMessage(conFactory, queue, message);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ttl.larku.service.ejb.impl.RegistrationService#dropStudentFromClass(int,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void dropStudentFromClass(int studentId, String courseCode,
			String startDate) {
		Student student = studentService.getStudent(studentId);
		List<ScheduledClass> classes = classService
				.getScheduledClassesByCourseCode(courseCode);
		ScheduledClass found = null;
		for (ScheduledClass sc : classes) {
			if (sc.getStartDate().equals(startDate)) {
				sc.removeStudent(student);
				student.dropClass(sc);
				found = sc;
				classService.updateScheduledClass(sc);
				studentService.updateStudent(student);
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ttl.larku.service.ejb.impl.RegistrationService#getStudentsForClass(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public List<Student> getStudentsForClass(String courseCode, String startDate) {
		List<ScheduledClass> classes = classService
				.getScheduledClassesByCourseCode(courseCode);
		for (ScheduledClass sc : classes) {
			if (sc.getStartDate().equals(startDate)) {
				return sc.getStudents();
			}
		}
		return new ArrayList<Student>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ttl.larku.service.ejb.impl.RegistrationService#getScheduledClasses()
	 */
	@Override
	public List<ScheduledClass> getScheduledClasses() {
		return classService.getAllScheduledClasses();
	}

	@Override
	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	@Override
	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	@Override
	public ClassService getClassService() {
		return classService;
	}

	public void setClassService(ClassService classService) {
		this.classService = classService;
	}

	@Override
	@Interceptors({SecurityInterceptor.class})
	public Student getStudent(int id) {
		Student student = studentService.getStudent(id);
		return student;
	}

	private Random random = new Random();
	@Override
	public Student createStudent(Student student) throws LarkUException {
		Student s = studentService.createStudent(student);

		/*
		int r = random.nextInt(100);
		if(r < 20 ) {
			throw new RuntimeException("Bwaaahhhahahahahahaha.  Data should not have been added");
		}
		else if(r < 90) {
			throw new LarkUException("Just for Kicks.  Check your data.  And weep!");
		}
		*/

		createdEvent.fire(student);
		return s;
	}

	@Override
	public Student createStudent(String name) {
		return studentService.createStudent(name);
	}

	@Override
	public void deleteStudent(int id) {
		studentService.deleteStudent(id);
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
		return classService
				.createScheduledClass(courseCode, startDate, endDate);
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
