package ttl.larku.service.ejb;

public interface RegistrationFacadeLocal extends RegistrationFacade{

	public CourseService getCourseService();
	public StudentService getStudentService();
	public ClassService getClassService();

}