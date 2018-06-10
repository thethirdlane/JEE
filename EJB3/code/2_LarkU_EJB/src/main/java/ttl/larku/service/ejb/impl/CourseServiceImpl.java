package ttl.larku.service.ejb.impl;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;
import ttl.larku.service.ejb.CourseService;

@ApplicationScoped
public class CourseServiceImpl implements CourseService {

	@Inject @DBQualifier(DBType.COURSE)
	private BaseDAO<Course> courseDAO;
	
	@PostConstruct
	public void init() {
		int i = 0;
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.CourseService#createCourse(java.lang.String, java.lang.String)
	 */
	@Override
	public Course createCourse(String code, String title) {
		Course course = new Course(code, title);
		course = courseDAO.create(course);
		
		return course;
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.CourseService#createCourse(ttl.larku.domain.Course)
	 */
	@Override
	public Course createCourse(Course course) {
		course = courseDAO.create(course);
		
		return course;
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.CourseService#deleteCourse(int)
	 */
	@Override
	public void deleteCourse(int id) {
		Course course = courseDAO.get(id);
		if(course != null) {
			courseDAO.delete(course);
		}
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.CourseService#updateCourse(ttl.larku.domain.Course)
	 */
	@Override
	public void updateCourse(Course course) {
		courseDAO.update(course);
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.CourseService#getCourseByCode(java.lang.String)
	 */
	@Override
	public Course getCourseByCode(String code) {
		List<Course> courses = courseDAO.getAll();
		for(Course course : courses) {
			if(course.getCode().equals(code)) {
				return course;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.CourseService#getCourse(int)
	 */
	@Override
	public Course getCourse(int id) {
		return courseDAO.get(id);
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.CourseService#getAllCourses()
	 */
	@Override
	public List<Course> getAllCourses() {
		return courseDAO.getAll();
	}
	
	public BaseDAO<Course> getCourseDAO() {
		return courseDAO;
	}

	public void setCourseDAO(BaseDAO<Course> courseDAO) {
		this.courseDAO = courseDAO;
	}

	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.CourseService#clear()
	 */
	@Override
	public void clear() {
		courseDAO.deleteStore();
		courseDAO.createStore();
	}
}
