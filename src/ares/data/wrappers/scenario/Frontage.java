
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Frontage.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Frontage">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NARROW"/>
 *     &lt;enumeration value="NORMAL"/>
 *     &lt;enumeration value="WIDE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Frontage", namespace = "ares")
@XmlEnum
public enum Frontage {

    NARROW,
    NORMAL,
    WIDE;

    public String value() {
        return name();
    }

    public static Frontage fromValue(String v) {
        return valueOf(v);
    }

}
