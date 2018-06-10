
package ttl.larku.soap.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for status.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="status">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FULL_TIME"/>
 *     &lt;enumeration value="PART_TIME"/>
 *     &lt;enumeration value="HIBERNATING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "status")
@XmlEnum
public enum Status {

    FULL_TIME,
    PART_TIME,
    HIBERNATING;

    public String value() {
        return name();
    }

    public static Status fromValue(String v) {
        return valueOf(v);
    }

}
