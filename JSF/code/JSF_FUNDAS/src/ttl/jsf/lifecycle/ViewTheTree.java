package ttl.jsf.lifecycle;

import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Named
public class ViewTheTree {

    private String name;

    public String getName() {
        System.out.println("Tracer.getName");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("Tracer.setName, name: " + name);
    }

    public void doit() {
        name = mixItUp(name);
        System.out.println("Tracer.doit, name: " + name);
    }


    private String mixItUp(String input) {
        List<String> chars = Arrays.asList(input.split(""));
        Collections.shuffle(chars);

        StringBuilder buf = new StringBuilder(64);
        for (String s : chars) {
            buf.append(s);
        }
        return buf.toString();
    }
}
