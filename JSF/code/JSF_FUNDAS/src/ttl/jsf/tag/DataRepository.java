package ttl.jsf.tag;

import ttl.jsf.domain.LineItem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@ApplicationScoped
public class DataRepository implements Serializable{

    private List<LineItem> lineItems;

    @PostConstruct
    public void init() {
        lineItems = new ArrayList<>(Arrays.asList(
                new LineItem("String", 10, 22.5),
                new LineItem("Fur Hat", 3, 2222),
                new LineItem("Bananas", 5, 35),
                new LineItem("Silver Forks", 8, 150),
                new LineItem("Flashlight", 1, 14.45),
                new LineItem("Tin Foil", 2, 8.5)
        ));
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }
}
