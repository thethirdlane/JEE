package ttl.jsf.comps;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ManagedBean
public class Comper {

    private static String clName = Comper.class.getSimpleName();
    private String name;
    private List<String> languages;

    private List<String> allLanguages;

    @PostConstruct
    public void init() {
        allLanguages = Arrays.asList("Smalltalk", "Ada", "Snobol", "Scheme");
    }

    public String getName() {
        System.out.println("Comper.getName");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println(clName + ".setName, name: " + name);
    }

    public List<String> getAllLanguages() {
        return allLanguages;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> langs) {
        this.languages = new ArrayList<String>(langs);
    }

    public void doit() {
        name = mixItUp(name);
        System.out.println("Action: Comper.doit, name: " + name);
    }

    public void listen(ActionEvent event) {
        //name = mixItUp(name);
        System.out.println("ActionListener: Comper.listen, source: " + event.getSource() + ", name: " + name);
    }

    /**
     * Immediate action
     */
    public void doitNow() {
        System.out.println("Immediate Action: Tracer.doitnow, name: " + name);
        //will throw nullpointer exception the first time
        //name = mixItUp(name);
    }

    public void valueChanged(ValueChangeEvent vce) {
        Object oldVal = vce.getOldValue();
        Object newVal = vce.getNewValue();

        System.out.println("ValueChangeListener: Tracer.valueChanged, oldVal: " + oldVal + ", newVal: " + newVal);

    }

    public void ajaxValueChanged(AjaxBehaviorEvent abe) {
        Object source = abe.getSource();
        String newVal = name;

        System.out.println("AjaxEvent: Tracer.ajaxValueChanged, newVal: " + newVal + ", source: " + source);
    }

    public void postAddToView(ComponentSystemEvent event) {
        System.out.println("PostAddToView: ,source: " + event.getSource());
    }

    public void preRenderComponent(ComponentSystemEvent event) {
        System.out.println("preRenderComponent: ,source: " + event.getSource());
    }

    public void preRenderView(ComponentSystemEvent event) {
        System.out.println("preRenderView: ,source: " + event.getSource());
    }
    public void preValidate(ComponentSystemEvent event) {
        System.out.println("preValidate: ,source: " + event.getSource());
    }
    public void postValidate(ComponentSystemEvent event) {
        System.out.println("postValidate: ,source: " + event.getSource());
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
