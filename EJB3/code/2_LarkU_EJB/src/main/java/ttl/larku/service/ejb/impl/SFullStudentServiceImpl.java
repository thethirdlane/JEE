package ttl.larku.service.ejb.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.inject.Inject;

import ttl.cdi.interceptors.Logged;
import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.ejb.StudentService;

@Stateful
@Remote
public class SFullStudentServiceImpl implements StudentService {

	@Inject @DBQualifier(DBType.STUDENT)
	private BaseDAO<Student> studentDAO;
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.StudentService#createStudent(java.lang.String)
	 */
	@Override
	public Student createStudent(String name) {
		Student student = new Student(name);
		student = studentDAO.create(student);
		
		return student;
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.StudentService#createStudent(ttl.larku.domain.Student)
	 */
	@Override
	public Student createStudent(Student student) {
		student = studentDAO.create(student);
		
		return student;
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.StudentService#deleteStudent(int)
	 */
	@Override
	public void deleteStudent(int id) {
		Student student = studentDAO.get(id);
		if(student != null) {
			studentDAO.delete(student);
		}
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.StudentService#updateStudent(ttl.larku.domain.Student)
	 */
	@Override
	public void updateStudent(Student student) {
		studentDAO.update(student);
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.StudentService#getStudent(int)
	 */
	@Override
	@Logged
	public Student getStudent(int id) {
		return studentDAO.get(id);
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.StudentService#getAllStudents()
	 */
	@Override
	public List<Student> getAllStudents() {
		return studentDAO.getAll();
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.StudentService#getStudentDAO()
	 */
	@Override
	public BaseDAO<Student> getStudentDAO() {
		return studentDAO;
	}

	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.StudentService#setStudentDAO(ttl.larku.dao.BaseDAO)
	 */
	@Override
	public void setStudentDAO(BaseDAO<Student> studentDAO) {
		this.studentDAO = studentDAO;
	}

	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.StudentService#clear()
	 */
	@Override
	public void clear() {
		studentDAO.deleteStore();
		studentDAO.createStore();
	}
	
	@Override
	public int getObjectId() {
		return hashCode();
	}
}
