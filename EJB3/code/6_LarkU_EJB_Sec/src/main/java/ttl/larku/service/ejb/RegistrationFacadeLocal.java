package ttl.larku.service.ejb;

import javax.ejb.Local;


public interface RegistrationFacadeLocal {

	public CourseService getCourseService();
	public StudentService getStudentService();
	public ClassService getClassService();

}