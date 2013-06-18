
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TurnLength.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="TurnLength">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SIX_HOURS"/>
 *     &lt;enumeration value="HALF_DAY"/>
 *     &lt;enumeration value="FULL_DAY"/>
 *     &lt;enumeration value="HALF_WEEK"/>
 *     &lt;enumeration value="FULL_WEEK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "TurnLength", namespace = "ares")
@XmlEnum
public enum TurnLength {

    SIX_HOURS,
    HALF_DAY,
    FULL_DAY,
    HALF_WEEK,
    FULL_WEEK;

    public String value() {
        return name();
    }

    public static TurnLength fromValue(String v) {
        return valueOf(v);
    }

}
