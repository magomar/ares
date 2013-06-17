
package ares.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Precipitation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Precipitation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HEAVY"/>
 *     &lt;enumeration value="MODERATE"/>
 *     &lt;enumeration value="LIGHT"/>
 *     &lt;enumeration value="OCCASIONAL"/>
 *     &lt;enumeration value="NO_PRECIPITATIONS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Precipitation", namespace = "ares")
@XmlEnum
public enum Precipitation {

    HEAVY,
    MODERATE,
    LIGHT,
    OCCASIONAL,
    NO_PRECIPITATIONS;

    public String value() {
        return name();
    }

    public static Precipitation fromValue(String v) {
        return valueOf(v);
    }

}
