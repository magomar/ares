
package ares.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Availability.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Availability">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NOT_DEPLOYED"/>
 *     &lt;enumeration value="TURN"/>
 *     &lt;enumeration value="EVENT"/>
 *     &lt;enumeration value="AVAILABLE"/>
 *     &lt;enumeration value="WITHDRAWN"/>
 *     &lt;enumeration value="EXITED"/>
 *     &lt;enumeration value="ELIMINATED"/>
 *     &lt;enumeration value="DISBANDED"/>
 *     &lt;enumeration value="DIVIDED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Availability", namespace = "ares")
@XmlEnum
public enum Availability {

    NOT_DEPLOYED,
    TURN,
    EVENT,
    AVAILABLE,
    WITHDRAWN,
    EXITED,
    ELIMINATED,
    DISBANDED,
    DIVIDED;

    public String value() {
        return name();
    }

    public static Availability fromValue(String v) {
        return valueOf(v);
    }

}
