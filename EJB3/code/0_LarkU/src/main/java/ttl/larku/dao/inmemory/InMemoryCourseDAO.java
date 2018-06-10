package ttl.larku.dao.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;

@DBQualifier(DBType.COURSE)
public class InMemoryCourseDAO implements BaseDAO<Course> {

	private Map<Integer, Course> courses = new HashMap<Integer, Course>();
	private static int nextId = 0;
	
	@PostConstruct
	public void init() {
		create(new Course("Math-101", "Baby Math"));
		create(new Course("Math-201", "Junior Math"));
		create(new Course("Math-445", "Are you kidding Math"));
		create(new Course("Physics-338", "Intro to Electrons"));
	}

	@Override
	public void update(Course updateObject) {
		if(courses.containsKey(updateObject.getId())) {
			courses.put(updateObject.getId(), updateObject);
		}
	}

	@Override
	public void delete(Course course) {
		courses.remove(course.getId());
	}

	@Override
	public Course create(Course newObject) {
		//Create a new Id
		int newId = nextId++;
		newObject.setId(newId);
		courses.put(newId, newObject);
		
		return newObject;
	}

	@Override
	public Course get(int id) {
		return courses.get(id);
	}

	@Override
	public List<Course> getAll() {
		return new ArrayList<Course>(courses.values());
	}
	
	@Override
	public void deleteStore() {
		courses = null;
	}
	
	@Override
	public void createStore() {
		courses = new HashMap<Integer, Course>();
	}
	
	public Map<Integer, Course> getCourses() {
		return courses;
	}

	public void setCourses(Map<Integer, Course> courses) {
		this.courses = courses;
	}
}
