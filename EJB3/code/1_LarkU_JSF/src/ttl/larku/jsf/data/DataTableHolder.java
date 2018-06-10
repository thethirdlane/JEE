package ttl.larku.jsf.data;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIData;

@ManagedBean(name="dth")
public class DataTableHolder {
	private UIData table;

	public UIData getTable() {
		return table;
	}

	public void setTable(UIData table) {
		this.table = table;
	}
	

}
