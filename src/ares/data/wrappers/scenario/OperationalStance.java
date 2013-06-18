
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperationalStance.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OperationalStance">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OFFENSIVE"/>
 *     &lt;enumeration value="DEFENSIVE"/>
 *     &lt;enumeration value="SECURITY"/>
 *     &lt;enumeration value="RECONNAISSANCE"/>
 *     &lt;enumeration value="FIXED"/>
 *     &lt;enumeration value="GARRISON"/>
 *     &lt;enumeration value="RESERVE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperationalStance", namespace = "ares")
@XmlEnum
public enum OperationalStance {

    OFFENSIVE,
    DEFENSIVE,
    SECURITY,
    RECONNAISSANCE,
    FIXED,
    GARRISON,
    RESERVE;

    public String value() {
        return name();
    }

    public static OperationalStance fromValue(String v) {
        return valueOf(v);
    }

}
