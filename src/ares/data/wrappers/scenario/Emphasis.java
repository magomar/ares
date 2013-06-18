
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Emphasis.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Emphasis">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MINIMIZE_LOSSES"/>
 *     &lt;enumeration value="LIMIT_LOSSES"/>
 *     &lt;enumeration value="IGNORE_LOSSES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Emphasis", namespace = "ares")
@XmlEnum
public enum Emphasis {

    MINIMIZE_LOSSES,
    LIMIT_LOSSES,
    IGNORE_LOSSES;

    public String value() {
        return name();
    }

    public static Emphasis fromValue(String v) {
        return valueOf(v);
    }

}
