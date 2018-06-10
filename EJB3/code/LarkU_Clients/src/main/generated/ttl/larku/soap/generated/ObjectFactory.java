
package ttl.larku.soap.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ttl.larku.soap.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAllStudents_QNAME = new QName("http://impl.ws.service.larku.ttl/", "getAllStudents");
    private final static QName _Student_QNAME = new QName("http://impl.ws.service.larku.ttl/", "student");
    private final static QName _Course_QNAME = new QName("http://impl.ws.service.larku.ttl/", "course");
    private final static QName _ScheduledClass_QNAME = new QName("http://impl.ws.service.larku.ttl/", "scheduledClass");
    private final static QName _GetAllStudentsResponse_QNAME = new QName("http://impl.ws.service.larku.ttl/", "getAllStudentsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ttl.larku.soap.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Student }
     * 
     */
    public Student createStudent() {
        return new Student();
    }

    /**
     * Create an instance of {@link GetAllStudents }
     * 
     */
    public GetAllStudents createGetAllStudents() {
        return new GetAllStudents();
    }

    /**
     * Create an instance of {@link Course }
     * 
     */
    public Course createCourse() {
        return new Course();
    }

    /**
     * Create an instance of {@link GetAllStudentsResponse }
     * 
     */
    public GetAllStudentsResponse createGetAllStudentsResponse() {
        return new GetAllStudentsResponse();
    }

    /**
     * Create an instance of {@link ScheduledClass }
     * 
     */
    public ScheduledClass createScheduledClass() {
        return new ScheduledClass();
    }

    /**
     * Create an instance of {@link Student.Classes }
     * 
     */
    public Student.Classes createStudentClasses() {
        return new Student.Classes();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllStudents }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.service.larku.ttl/", name = "getAllStudents")
    public JAXBElement<GetAllStudents> createGetAllStudents(GetAllStudents value) {
        return new JAXBElement<GetAllStudents>(_GetAllStudents_QNAME, GetAllStudents.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Student }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.service.larku.ttl/", name = "student")
    public JAXBElement<Student> createStudent(Student value) {
        return new JAXBElement<Student>(_Student_QNAME, Student.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Course }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.service.larku.ttl/", name = "course")
    public JAXBElement<Course> createCourse(Course value) {
        return new JAXBElement<Course>(_Course_QNAME, Course.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ScheduledClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.service.larku.ttl/", name = "scheduledClass")
    public JAXBElement<ScheduledClass> createScheduledClass(ScheduledClass value) {
        return new JAXBElement<ScheduledClass>(_ScheduledClass_QNAME, ScheduledClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllStudentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.service.larku.ttl/", name = "getAllStudentsResponse")
    public JAXBElement<GetAllStudentsResponse> createGetAllStudentsResponse(GetAllStudentsResponse value) {
        return new JAXBElement<GetAllStudentsResponse>(_GetAllStudentsResponse_QNAME, GetAllStudentsResponse.class, null, value);
    }

}
