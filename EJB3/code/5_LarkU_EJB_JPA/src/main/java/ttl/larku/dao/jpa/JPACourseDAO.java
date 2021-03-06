package ttl.larku.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;

@DBQualifier(DBType.JPA_COURSE)
public class JPACourseDAO implements BaseDAO<Course> {

	@PersistenceContext(unitName="LarkUPU_EE")
	private EntityManager entityManager;
	
	@Override
	public void update(Course updateObject) {
		entityManager.merge(updateObject);
	}

	@Override
	public void delete(Course objToDelete) {
		Course managed = entityManager.find(Course.class, objToDelete.getId());
		entityManager.remove(managed);
	}

	@Override
	public Course create(Course newObject) {
		entityManager.persist(newObject);
		return newObject;
	}

	@Override
	public Course get(int id) {
		return entityManager.find(Course.class, id);
	}

	@Override
	public List<Course> getAll() {
		TypedQuery<Course> query = entityManager.createQuery("Select s from Course s", Course.class);
		List<Course> courses = query.getResultList();
		return courses;
	}
	
	@Override
	public void deleteStore() {
		Query query = entityManager.createQuery("Delete from Course");
		query.executeUpdate();
	}
	
	@Override
	public void createStore() {
	}
}
