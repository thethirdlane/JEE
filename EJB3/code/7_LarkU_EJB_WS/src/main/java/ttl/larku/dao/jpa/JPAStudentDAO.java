package ttl.larku.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
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
		return entityManager.find(Student.class, id);
	}

	@Override
	public List<Student> getAll() {
	 	Query query = entityManager.createQuery("Select s from Student s");

	 	//Use this to cause a refresh of the cache.  Helps when data may
	 	//have been changed underneath you
	 	//query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		@SuppressWarnings("unchecked")
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
