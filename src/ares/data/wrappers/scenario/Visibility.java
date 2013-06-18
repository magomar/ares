
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Visibility.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Visibility">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FAIR"/>
 *     &lt;enumeration value="HAZE"/>
 *     &lt;enumeration value="OVERCAST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Visibility", namespace = "ares")
@XmlEnum
public enum Visibility {

    FAIR,
    HAZE,
    OVERCAST;

    public String value() {
        return name();
    }

    public static Visibility fromValue(String v) {
        return valueOf(v);
    }

}
