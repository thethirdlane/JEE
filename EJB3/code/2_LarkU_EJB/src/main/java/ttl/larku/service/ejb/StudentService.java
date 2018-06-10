package ttl.larku.service.ejb;

import java.util.List;

import ttl.cdi.interceptors.Logged;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;

public interface StudentService {

	public abstract Student createStudent(String name);

	public abstract Student createStudent(Student student);

	public abstract void deleteStudent(int id);

	public abstract void updateStudent(Student student);

	public abstract Student getStudent(int id);

	public abstract List<Student> getAllStudents();

	public abstract BaseDAO<Student> getStudentDAO();

	public abstract void setStudentDAO(BaseDAO<Student> studentDAO);

	public abstract void clear();
	
	public abstract int getObjectId();

}