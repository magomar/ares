
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupportScope.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="SupportScope">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INTERNAL_SUPPORT"/>
 *     &lt;enumeration value="ARMY_SUPPORT"/>
 *     &lt;enumeration value="FORCE_SUPPORT"/>
 *     &lt;enumeration value="FREE_SUPPORT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "SupportScope", namespace = "ares")
@XmlEnum
public enum SupportScope {

    INTERNAL_SUPPORT,
    ARMY_SUPPORT,
    FORCE_SUPPORT,
    FREE_SUPPORT;

    public String value() {
        return name();
    }

    public static SupportScope fromValue(String v) {
        return valueOf(v);
    }

}
