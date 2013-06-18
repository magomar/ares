
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TerrainFeature.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TerrainFeature">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ANCHORAGE"/>
 *     &lt;enumeration value="AIRFIELD"/>
 *     &lt;enumeration value="PEAK"/>
 *     &lt;enumeration value="CONTAMINATED"/>
 *     &lt;enumeration value="NON_PLAYABLE"/>
 *     &lt;enumeration value="MUDDY"/>
 *     &lt;enumeration value="SNOWY"/>
 *     &lt;enumeration value="BRIDGE_DESTROYED"/>
 *     &lt;enumeration value="FROZEN"/>
 *     &lt;enumeration value="EXCLUDED_1"/>
 *     &lt;enumeration value="EXCLUDED_2"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TerrainFeature", namespace = "ares")
@XmlEnum
public enum TerrainFeature {

    ANCHORAGE,
    AIRFIELD,
    PEAK,
    CONTAMINATED,
    NON_PLAYABLE,
    MUDDY,
    SNOWY,
    BRIDGE_DESTROYED,
    FROZEN,
    EXCLUDED_1,
    EXCLUDED_2;

    public String value() {
        return name();
    }

    public static TerrainFeature fromValue(String v) {
        return valueOf(v);
    }

}
