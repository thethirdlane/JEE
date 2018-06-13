package ttl.larku.service.ejb.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.ejb.ClassService;
import ttl.larku.service.ejb.CourseService;
import ttl.larku.service.ejb.RegistrationFacade;
import ttl.larku.service.ejb.StudentService;

@Stateless
@Remote(RegistrationFacade.class)
public class RegistrationFacadeImpl implements RegistrationFacade{

	private CourseService courseService;
	private ClassService classService;

	@EJB(beanName="SLStudentServiceImpl")
	private StudentService studentService;


	public RegistrationFacadeImpl() {
	}
	
	@Inject
	public RegistrationFacadeImpl(CourseService cs, ClassService classService) {
		courseService = cs;
		//studentService = ss;
		this.classService = classService;
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


	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.RegistrationService#getCourseService()
	 */
	@Override
	public CourseService getCourseService() {
		return courseService;
	}


	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}


	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.RegistrationService#getStudentService()
	 */
	@Override
	public StudentService getStudentService() {
		return studentService;
	}


	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}


	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.RegistrationService#getClassService()
	 */
	@Override
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
}
