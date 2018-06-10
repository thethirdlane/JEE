package ttl.larku.jsf.data;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ttl.larku.domain.Course;
import ttl.larku.domain.Student;

//@ManagedBean
//@ViewScoped
public class CourseList{
	
	private List<Course> list;
	
	public CourseList() {
		list = null;
	}
	public CourseList(List<Course> list) { 
		this.list = list; 
	}
	public List<Course> getList() {
		return list;
	}
	
	public void setList(List<Course> list) {
		this.list = list;
	}

}
