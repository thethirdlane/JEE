package ttl.larku.jsf.data;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ttl.larku.domain.ScheduledClass;

//@ManagedBean
//@ViewScoped
public class ClassList {
	
	private List<ScheduledClass> list;
	
	public ClassList() {
		list = null;
	}
	public ClassList(List<ScheduledClass> list) { 
		this.list = list; 
	}
	public List<ScheduledClass> getList() {
		return list;
	}
	
	public void setList(List<ScheduledClass> list) {
		this.list = list;
	}

}
