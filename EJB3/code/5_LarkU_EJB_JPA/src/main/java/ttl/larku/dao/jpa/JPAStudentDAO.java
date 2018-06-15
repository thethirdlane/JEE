package ttl.larku.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

@DBQualifier(DBType.JPA_STUDENT)
public class JPAStudentDAO implements BaseDAO<Student> {

	@PersistenceContext(unitName="LarkUPU_EE")
	private EntityManager entityManager;
	
	
	@Override
	public void update(Student updateObject) {
		entityManager.merge(updateObject);
	}

	@Override
	public void delete(Student objToDelete) {
		Student managed = entityManager.find(Student.class, objToDelete.getId());
		entityManager.remove(managed);
	}

	@Override
	public Student create(Student newObject) {
		entityManager.persist(newObject);
		return newObject;
	}

	@Override
	public Student get(int id) {
        TypedQuery<Student> query = entityManager.
                createQuery("Select s from Student s left join fetch s.classes c where s.id = :sid",
                        Student.class);
        query.setParameter("sid", id);
        Student s = query.getSingleResult();
        List<ScheduledClass> l = s.getClasses();
        return s;
	}

	@Override
	public List<Student> getAll() {
		TypedQuery<Student> query = entityManager.createQuery("Select s from Student s left join fetch s.classes",
                Student.class);
		List<Student> students = query.getResultList();
		return students;
	}
	
	@Override
	public void deleteStore() {
		Query query = entityManager.createQuery("Delete from Student");
		query.executeUpdate();
	}
	
	@Override
	public void createStore() {
	}

}
