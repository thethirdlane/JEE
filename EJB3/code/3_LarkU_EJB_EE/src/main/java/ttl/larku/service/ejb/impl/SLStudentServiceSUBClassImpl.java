package ttl.larku.service.ejb.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.inject.Specializes;

import ttl.larku.domain.Student;

/**
 * Use for CDI Specialization example.  @Specializes here will make
 * the SLStudentServiceImpl become a non candidate for Injection
*/

@Stateless
@Local
@Specializes
public class SLStudentServiceSUBClassImpl extends SLStudentServiceImpl 
{
	@Override
	public List<Student> getAllStudents() {
		return super.getAllStudents();
	}

}
