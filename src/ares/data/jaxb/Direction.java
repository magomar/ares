
package ares.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Direction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Direction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="NE"/>
 *     &lt;enumeration value="SE"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="SW"/>
 *     &lt;enumeration value="NW"/>
 *     &lt;enumeration value="C"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Direction", namespace = "ares")
@XmlEnum
public enum Direction {

    N,
    NE,
    SE,
    S,
    SW,
    NW,
    C;

    public String value() {
        return name();
    }

    public static Direction fromValue(String v) {
        return valueOf(v);
    }

}
