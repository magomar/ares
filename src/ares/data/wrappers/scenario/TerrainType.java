
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TerrainType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TerrainType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OPEN"/>
 *     &lt;enumeration value="ARID"/>
 *     &lt;enumeration value="SAND"/>
 *     &lt;enumeration value="DUNES"/>
 *     &lt;enumeration value="BADLANDS"/>
 *     &lt;enumeration value="HILLS"/>
 *     &lt;enumeration value="MOUNTAINS"/>
 *     &lt;enumeration value="ALPINE"/>
 *     &lt;enumeration value="MARSH"/>
 *     &lt;enumeration value="FLOODED_MARSH"/>
 *     &lt;enumeration value="SHALLOW_WATER"/>
 *     &lt;enumeration value="DEEP_WATER"/>
 *     &lt;enumeration value="CROPLANDS"/>
 *     &lt;enumeration value="BOCAGE_HEDGEROW"/>
 *     &lt;enumeration value="URBAN"/>
 *     &lt;enumeration value="DENSE_URBAN"/>
 *     &lt;enumeration value="URBAN_RUIN"/>
 *     &lt;enumeration value="DENSE_URBAN_RUIN"/>
 *     &lt;enumeration value="ROCKY"/>
 *     &lt;enumeration value="ESCARPMENT"/>
 *     &lt;enumeration value="MAJOR_ESCARPMENT"/>
 *     &lt;enumeration value="WADY"/>
 *     &lt;enumeration value="RIVER"/>
 *     &lt;enumeration value="SUPER_RIVER"/>
 *     &lt;enumeration value="CANAL"/>
 *     &lt;enumeration value="SUPER_CANAL"/>
 *     &lt;enumeration value="EVERGREEN_FOREST"/>
 *     &lt;enumeration value="FOREST"/>
 *     &lt;enumeration value="LIGHT_WOODS"/>
 *     &lt;enumeration value="JUNGLE"/>
 *     &lt;enumeration value="FORTIFICATION"/>
 *     &lt;enumeration value="ROAD"/>
 *     &lt;enumeration value="IMPROVED_ROAD"/>
 *     &lt;enumeration value="RAIL"/>
 *     &lt;enumeration value="BROKEN_RAIL"/>
 *     &lt;enumeration value="SHALLOW_WATER_DECORATOR"/>
 *     &lt;enumeration value="DEEP_WATER_DECORATOR"/>
 *     &lt;enumeration value="BORDER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TerrainType", namespace = "ares")
@XmlEnum
public enum TerrainType {

    OPEN,
    ARID,
    SAND,
    DUNES,
    BADLANDS,
    HILLS,
    MOUNTAINS,
    ALPINE,
    MARSH,
    FLOODED_MARSH,
    SHALLOW_WATER,
    DEEP_WATER,
    CROPLANDS,
    BOCAGE_HEDGEROW,
    URBAN,
    DENSE_URBAN,
    URBAN_RUIN,
    DENSE_URBAN_RUIN,
    ROCKY,
    ESCARPMENT,
    MAJOR_ESCARPMENT,
    WADY,
    RIVER,
    SUPER_RIVER,
    CANAL,
    SUPER_CANAL,
    EVERGREEN_FOREST,
    FOREST,
    LIGHT_WOODS,
    JUNGLE,
    FORTIFICATION,
    ROAD,
    IMPROVED_ROAD,
    RAIL,
    BROKEN_RAIL,
    SHALLOW_WATER_DECORATOR,
    DEEP_WATER_DECORATOR,
    BORDER;

    public String value() {
        return name();
    }

    public static TerrainType fromValue(String v) {
        return valueOf(v);
    }

}
