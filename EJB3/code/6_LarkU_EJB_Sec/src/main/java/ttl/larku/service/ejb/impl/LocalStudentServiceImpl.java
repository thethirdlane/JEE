package ttl.larku.service.ejb.impl;

import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ttl.cdi.interceptors.Logged;
import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.ejb.StudentService;

@Stateless
@Local
public class LocalStudentServiceImpl implements StudentService {

	@Resource
	private SessionContext sessionContext;
	
	@Inject @DBQualifier(DBType.JPA_STUDENT)
	private BaseDAO<Student> studentDAO;
	
	@Override
	public Student createStudent(String name) {
		Student student = new Student(name);
		student = studentDAO.create(student);
		
		return student;
	}
	
	@Override
	public Student createStudent(Student student) {
		student = studentDAO.create(student);
		
		return student;
	}
	
	@Override
	@RolesAllowed("admin")
	public void deleteStudent(int id) {
		Student student = studentDAO.get(id);
		if(student != null) {
			studentDAO.delete(student);
		}
	}
	
	@Override
	public void updateStudent(Student student) {
		studentDAO.update(student);
	}
	
	@Override
	@Logged
	public Student getStudent(int id) {
		Student student = studentDAO.get(id);
		return student;
	}
	
	@Override
	public List<Student> getAllStudents() {
		return studentDAO.getAll();
	}
	
	@Override
	public BaseDAO<Student> getStudentDAO() {
		return studentDAO;
	}

	@Override
	public void setStudentDAO(BaseDAO<Student> studentDAO) {
		this.studentDAO = studentDAO;
	}

	@Override
	public void clear() {
		studentDAO.deleteStore();
		studentDAO.createStore();
	}
}
