package ttl.larku.jsf.data;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ListHolder<T> {
	
	private List<T> list;
	
	public ListHolder() {}
	public ListHolder(List<T> list) { 
		this.list = list; 
	}
	public List<T> getList() {
		return list;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}

}
