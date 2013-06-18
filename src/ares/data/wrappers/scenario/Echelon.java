
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Echelon.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="Echelon">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SECTION"/>
 *     &lt;enumeration value="SQUAD"/>
 *     &lt;enumeration value="PLATOON"/>
 *     &lt;enumeration value="COMPANY"/>
 *     &lt;enumeration value="BATTALION"/>
 *     &lt;enumeration value="REGIMENT"/>
 *     &lt;enumeration value="BRIGADE"/>
 *     &lt;enumeration value="DIVISION"/>
 *     &lt;enumeration value="CORPS"/>
 *     &lt;enumeration value="ARMY"/>
 *     &lt;enumeration value="ARMY_GROUP"/>
 *     &lt;enumeration value="REGION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "Echelon", namespace = "ares")
@XmlEnum
public enum Echelon {

    SECTION,
    SQUAD,
    PLATOON,
    COMPANY,
    BATTALION,
    REGIMENT,
    BRIGADE,
    DIVISION,
    CORPS,
    ARMY,
    ARMY_GROUP,
    REGION;

    public String value() {
        return name();
    }

    public static Echelon fromValue(String v) {
        return valueOf(v);
    }

}
