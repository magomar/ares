
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Temperature.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="Temperature">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FROZEN_3"/>
 *     &lt;enumeration value="FROZEN_2"/>
 *     &lt;enumeration value="FROZEN_1"/>
 *     &lt;enumeration value="COLD"/>
 *     &lt;enumeration value="COOL"/>
 *     &lt;enumeration value="TEMPERATE"/>
 *     &lt;enumeration value="WARM"/>
 *     &lt;enumeration value="HOT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "Temperature", namespace = "ares")
@XmlEnum
public enum Temperature {

    FROZEN_3,
    FROZEN_2,
    FROZEN_1,
    COLD,
    COOL,
    TEMPERATE,
    WARM,
    HOT;

    public String value() {
        return name();
    }

    public static Temperature fromValue(String v) {
        return valueOf(v);
    }

}
