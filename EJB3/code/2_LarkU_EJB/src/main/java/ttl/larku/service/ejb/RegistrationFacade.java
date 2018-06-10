package ttl.larku.service.ejb;

import java.util.List;

import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.ejb.StudentService;

public interface RegistrationFacade {

	public abstract ScheduledClass addNewClassToSchedule(String courseCode,
			String startDate, String endDate);

	public abstract void registerStudentForClass(int studentId,
			String courseCode, String startDate);

	public abstract void dropStudentFromClass(int studentId, String courseCode,
			String startDate);

	public abstract List<Student> getStudentsForClass(String courseCode,
			String startDate);

	public abstract List<ScheduledClass> getScheduledClasses();

	public Student getStudent(int id);
	public Student createStudent(Student student);
	public Student createStudent(String name);

	public List<ScheduledClass> getAllScheduledClasses();

	public abstract CourseService getCourseService();

	public abstract StudentService getStudentService();

	public abstract ClassService getClassService();

}