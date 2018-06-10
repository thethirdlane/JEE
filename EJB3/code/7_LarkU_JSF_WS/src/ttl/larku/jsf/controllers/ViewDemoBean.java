package ttl.larku.jsf.controllers;

import ttl.larku.domain.Student;
import ttl.larku.service.ejb.RegistrationFacade;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

@Named
public class ViewDemoBean {

    private int sid;
    private Student student;

    @EJB
    private RegistrationFacade regFacade;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void retrieveStudent() {
       student = regFacade.getStudent(sid);
    }

    public Student getStudent() {
        return student;
    }
}


