package ttl.larku.service;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ttl.cdi.interceptors.Logged;
import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;

@ManagedBean
@ApplicationScoped
public class StudentService {

	@Inject @DBQualifier(DBType.STUDENT)
	private BaseDAO<Student> studentDAO;
	
	public Student createStudent(String name) {
		Student student = new Student(name);
		student = studentDAO.create(student);
		
		return student;
	}
	
	public Student createStudent(Student student) {
		student = studentDAO.create(student);
		
		return student;
	}
	
	public void deleteStudent(int id) {
		Student student = studentDAO.get(id);
		if(student != null) {
			studentDAO.delete(student);
		}
	}
	
	public void updateStudent(Student student) {
		studentDAO.update(student);
	}
	
	@Logged
	public Student getStudent(int id) {
		return studentDAO.get(id);
	}
	
	public List<Student> getAllStudents() {
		return studentDAO.getAll();
	}
	
	public BaseDAO<Student> getStudentDAO() {
		return studentDAO;
	}

	public void setStudentDAO(BaseDAO<Student> studentDAO) {
		this.studentDAO = studentDAO;
	}

	public void clear() {
		studentDAO.deleteStore();
		studentDAO.createStore();
	}
}
