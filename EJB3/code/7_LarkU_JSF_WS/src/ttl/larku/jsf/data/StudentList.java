package ttl.larku.jsf.data;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ttl.larku.domain.Student;

//@ManagedBean
//@ViewScoped
public class StudentList{
	
	private List<Student> list;
	
	public StudentList() {
		list = null;
	}
	public StudentList(List<Student> list) { 
		this.list = list; 
	}
	public List<Student> getList() {
		return list;
	}
	
	public void setList(List<Student> list) {
		this.list = list;
	}

}
