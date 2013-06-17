
package ares.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OpState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OpState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DEPLOYING"/>
 *     &lt;enumeration value="DEPLOYED"/>
 *     &lt;enumeration value="ASSEMBLING"/>
 *     &lt;enumeration value="MOBILE"/>
 *     &lt;enumeration value="MOVING"/>
 *     &lt;enumeration value="RETREATING"/>
 *     &lt;enumeration value="WITHDRAWING"/>
 *     &lt;enumeration value="ROUTING"/>
 *     &lt;enumeration value="REORGANIZING"/>
 *     &lt;enumeration value="EMBARKING"/>
 *     &lt;enumeration value="EMBARKED"/>
 *     &lt;enumeration value="DEFENDING"/>
 *     &lt;enumeration value="ASSAULTING"/>
 *     &lt;enumeration value="ATTACKING"/>
 *     &lt;enumeration value="BOMBARDING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OpState", namespace = "ares")
@XmlEnum
public enum OpState {

    DEPLOYING,
    DEPLOYED,
    ASSEMBLING,
    MOBILE,
    MOVING,
    RETREATING,
    WITHDRAWING,
    ROUTING,
    REORGANIZING,
    EMBARKING,
    EMBARKED,
    DEFENDING,
    ASSAULTING,
    ATTACKING,
    BOMBARDING;

    public String value() {
        return name();
    }

    public static OpState fromValue(String v) {
        return valueOf(v);
    }

}
