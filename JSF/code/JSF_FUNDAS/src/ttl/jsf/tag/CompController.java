package ttl.jsf.tag;

import ttl.jsf.domain.LineItem;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CompController implements Serializable{

    @Inject
    private DataRepository repo;

    public List<LineItem> getLineItems() {
        return repo.getLineItems();
    }
}
