package ttl.jsf.lifecycle;

import ttl.jsf.comps.Comper;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.*;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.transform.sax.SAXSource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Named
@ViewScoped
public class Tracer implements Serializable {

    private String name;
    private String nameA;

    private String otherProp;

    transient Comper comp = new Comper();

    private int age;

    public Tracer() {
        int i = 0;
        System.out.println("cp: " + System.getProperty("java.class.path"));
    }

    public String getNameA() {
        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public String getName() {
        System.out.println("Tracer.getName");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("Tracer.setName, name: " + name);
    }

    public int getAge() {
        System.out.println("Tracer.getAge, age: " + age);
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        System.out.println("Tracer.setAge, age: " + age);
    }

    public void doit() {
        name = mixItUp(name);
        System.out.println("Action: Tracer.doit, name: " + name);

        //return "lifecycle";

    }

    public String doitA() {
        nameA = mixItUp(nameA);
        System.out.println("Ajax Action: Tracer.doit, name: " + name);

        return "lifecycle";
    }

    public void listen(ActionEvent event) {
        //name = mixItUp(name);
        System.out.println("ActionListener: Tracer.listen, source: " + event.getSource() + ", name: " + name);
    }

    /**
     * Immediate action
     */
    public void doitNow() {
        System.out.println("Immediate Action: Tracer.doitnow, name: " + name);
        //will throw nullpointer exception the first time
        //name = mixItUp(name);
    }


    public void valueChanged(ValueChangeEvent vce) throws ValidatorException {
        String oldVal = (String) vce.getOldValue();
        String newVal = (String) vce.getNewValue();

        if (oldVal != null && oldVal.length() < newVal.length()) {
            UIComponent comp = (UIComponent) vce.getSource();
            /*
            FacesContext.getCurrentInstance().addMessage(comp.getClientId(),
                    new FacesMessage("No", "No you can't!!"));
            FacesContext.getCurrentInstance().renderResponse();
            */
            throw new ValidatorException(new FacesMessage
                    (FacesMessage.SEVERITY_ERROR,
                            "No!", "Not allowed!"));
        }

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
