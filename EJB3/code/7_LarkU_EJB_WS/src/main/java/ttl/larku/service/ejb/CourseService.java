package ttl.larku.service.ejb;

import java.util.List;

import ttl.larku.domain.Course;

public interface CourseService {

	public abstract Course createCourse(String code, String title);

	public abstract Course createCourse(Course course);

	public abstract void deleteCourse(int id);

	public abstract void updateCourse(Course course);

	public abstract Course getCourseByCode(String code);

	public abstract Course getCourse(int id);

	public abstract List<Course> getAllCourses();

	public abstract void clear();

}