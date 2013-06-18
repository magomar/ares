
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClimateArea.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ClimateArea">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NORTHERN"/>
 *     &lt;enumeration value="SOUTHERN"/>
 *     &lt;enumeration value="ECUATORIAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ClimateArea", namespace = "ares")
@XmlEnum
public enum ClimateArea {

    NORTHERN,
    SOUTHERN,
    ECUATORIAL;

    public String value() {
        return name();
    }

    public static ClimateArea fromValue(String v) {
        return valueOf(v);
    }

}
