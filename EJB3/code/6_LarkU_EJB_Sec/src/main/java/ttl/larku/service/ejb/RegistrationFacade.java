package ttl.larku.service.ejb;

import java.util.List;

import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.exception.LarkUException;

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
	public Student createStudent(Student student) throws LarkUException;
	public Student createStudent(String name);
	public void deleteStudent(int id);
	public List<Student> getAllStudents();

	public List<ScheduledClass> getAllScheduledClasses();
	public ScheduledClass createScheduledClass(String courseCode, String startDate, String endDate);
	public ScheduledClass getScheduledClass(int id);

	public List<Course> getAllCourses();
	public Course getCourse(int id);
	public Course createCourse(Course course);


}