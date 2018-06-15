package ttl.larku.service.ejb.impl;

import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.service.ejb.ClassService;
import ttl.larku.service.ejb.CourseService;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local
public class ClassServiceImpl implements ClassService {
	
	@EJB
	private CourseService courseService;
	
	@Inject @DBQualifier(DBType.CLASS)
	private BaseDAO<ScheduledClass> classDAO;
	
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.ClassService#createScheduledClass(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ScheduledClass createScheduledClass(String courseCode, String startDate, String endDate) {
		Course course = courseService.getCourseByCode(courseCode);
		if(course != null) {
			ScheduledClass sClass = new ScheduledClass(course, startDate, endDate);
			sClass = classDAO.create(sClass);
			return sClass;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.ClassService#deleteScheduledClass(int)
	 */
	@Override
	public void deleteScheduledClass(int id) {
		ScheduledClass course = classDAO.get(id);
		if(course != null) {
			classDAO.delete(course);
		}
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.ClassService#updateScheduledClass(ttl.larku.domain.ScheduledClass)
	 */
	@Override
	public void updateScheduledClass(ScheduledClass course) {
		classDAO.update(course);
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.ClassService#getScheduledClassesByCourseCode(java.lang.String)
	 */
	@Override
	public List<ScheduledClass> getScheduledClassesByCourseCode(String code) {
		List<ScheduledClass> result = new ArrayList<ScheduledClass>();
		for(ScheduledClass sc : classDAO.getAll()) {
			if(sc.getCourse().getCode().equals(code)) {
				result.add(sc);
			}
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.ClassService#getScheduledClass(int)
	 */
	@Override
	public ScheduledClass getScheduledClass(int id) {
		return classDAO.get(id);
	}
	
	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.ClassService#getAllScheduledClasses()
	 */
	@Override
	public List<ScheduledClass> getAllScheduledClasses() {
		return classDAO.getAll();
	}
	
	public BaseDAO<ScheduledClass> getClassDAO() {
		return classDAO;
	}

	public void setClassDAO(BaseDAO<ScheduledClass> classDAO) {
		this.classDAO = classDAO;
	}


	/* (non-Javadoc)
	 * @see ttl.larku.service.ejb.impl.ClassService#clear()
	 */
	@Override
	public void clear() {
		classDAO.deleteStore();
		classDAO.createStore();
	}
}
