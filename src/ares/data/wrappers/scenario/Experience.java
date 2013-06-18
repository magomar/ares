
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Experience.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Experience">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UNTRIED"/>
 *     &lt;enumeration value="VETERAN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Experience", namespace = "ares")
@XmlEnum
public enum Experience {

    UNTRIED,
    VETERAN;

    public String value() {
        return name();
    }

    public static Experience fromValue(String v) {
        return valueOf(v);
    }

}
