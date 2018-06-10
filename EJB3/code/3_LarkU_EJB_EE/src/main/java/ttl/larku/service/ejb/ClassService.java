package ttl.larku.service.ejb;

import java.util.List;

import ttl.larku.domain.ScheduledClass;

public interface ClassService {

	public abstract ScheduledClass createScheduledClass(String courseCode,
			String startDate, String endDate);

	public abstract void deleteScheduledClass(int id);

	public abstract void updateScheduledClass(ScheduledClass course);

	public abstract List<ScheduledClass> getScheduledClassesByCourseCode(
			String code);

	public abstract ScheduledClass getScheduledClass(int id);

	public abstract List<ScheduledClass> getAllScheduledClasses();

	public abstract void clear();

}