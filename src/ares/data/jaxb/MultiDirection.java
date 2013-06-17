
package ares.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MultiDirection.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MultiDirection">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="NE"/>
 *     &lt;enumeration value="N_NE"/>
 *     &lt;enumeration value="SE"/>
 *     &lt;enumeration value="N_SE"/>
 *     &lt;enumeration value="NE_SE"/>
 *     &lt;enumeration value="N_NE_SE"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="N_S"/>
 *     &lt;enumeration value="NE_S"/>
 *     &lt;enumeration value="N_NE_S"/>
 *     &lt;enumeration value="SE_S"/>
 *     &lt;enumeration value="N_SE_S"/>
 *     &lt;enumeration value="NE_SE_S"/>
 *     &lt;enumeration value="N_NE_SE_S"/>
 *     &lt;enumeration value="SW"/>
 *     &lt;enumeration value="N_SW"/>
 *     &lt;enumeration value="NE_SW"/>
 *     &lt;enumeration value="N_NE_SW"/>
 *     &lt;enumeration value="SE_SW"/>
 *     &lt;enumeration value="N_SE_SW"/>
 *     &lt;enumeration value="NE_SE_SW"/>
 *     &lt;enumeration value="N_NE_SE_SW"/>
 *     &lt;enumeration value="S_SW"/>
 *     &lt;enumeration value="N_S_SW"/>
 *     &lt;enumeration value="NE_S_SW"/>
 *     &lt;enumeration value="N_NE_S_SW"/>
 *     &lt;enumeration value="SE_S_SW"/>
 *     &lt;enumeration value="N_SE_S_SW"/>
 *     &lt;enumeration value="NE_SE_S_SW"/>
 *     &lt;enumeration value="N_NE_SE_S_SW"/>
 *     &lt;enumeration value="NW"/>
 *     &lt;enumeration value="N_NW"/>
 *     &lt;enumeration value="NE_NW"/>
 *     &lt;enumeration value="N_NE_NW"/>
 *     &lt;enumeration value="SE_NW"/>
 *     &lt;enumeration value="N_SE_NW"/>
 *     &lt;enumeration value="NE_SE_NW"/>
 *     &lt;enumeration value="N_NE_SE_NW"/>
 *     &lt;enumeration value="S_NW"/>
 *     &lt;enumeration value="N_S_NW"/>
 *     &lt;enumeration value="NE_S_NW"/>
 *     &lt;enumeration value="N_NE_S_NW"/>
 *     &lt;enumeration value="SE_S_NW"/>
 *     &lt;enumeration value="N_SE_S_NW"/>
 *     &lt;enumeration value="NE_SE_S_NW"/>
 *     &lt;enumeration value="N_NE_SE_S_NW"/>
 *     &lt;enumeration value="SW_NW"/>
 *     &lt;enumeration value="N_SW_NW"/>
 *     &lt;enumeration value="NE_SW_NW"/>
 *     &lt;enumeration value="N_NE_SW_NW"/>
 *     &lt;enumeration value="SE_SW_NW"/>
 *     &lt;enumeration value="N_SE_SW_NW"/>
 *     &lt;enumeration value="NE_SE_SW_NW"/>
 *     &lt;enumeration value="N_NE_SE_SW_NW"/>
 *     &lt;enumeration value="S_SW_NW"/>
 *     &lt;enumeration value="N_S_SW_NW"/>
 *     &lt;enumeration value="NE_S_SW_NW"/>
 *     &lt;enumeration value="N_NE_S_SW_NW"/>
 *     &lt;enumeration value="SE_S_SW_NW"/>
 *     &lt;enumeration value="N_SE_S_SW_NW"/>
 *     &lt;enumeration value="NE_SE_S_SW_NW"/>
 *     &lt;enumeration value="N_NE_SE_S_SW_NW"/>
 *     &lt;enumeration value="C"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MultiDirection", namespace = "ares")
@XmlEnum
public enum MultiDirection {

    N,
    NE,
    N_NE,
    SE,
    N_SE,
    NE_SE,
    N_NE_SE,
    S,
    N_S,
    NE_S,
    N_NE_S,
    SE_S,
    N_SE_S,
    NE_SE_S,
    N_NE_SE_S,
    SW,
    N_SW,
    NE_SW,
    N_NE_SW,
    SE_SW,
    N_SE_SW,
    NE_SE_SW,
    N_NE_SE_SW,
    S_SW,
    N_S_SW,
    NE_S_SW,
    N_NE_S_SW,
    SE_S_SW,
    N_SE_S_SW,
    NE_SE_S_SW,
    N_NE_SE_S_SW,
    NW,
    N_NW,
    NE_NW,
    N_NE_NW,
    SE_NW,
    N_SE_NW,
    NE_SE_NW,
    N_NE_SE_NW,
    S_NW,
    N_S_NW,
    NE_S_NW,
    N_NE_S_NW,
    SE_S_NW,
    N_SE_S_NW,
    NE_SE_S_NW,
    N_NE_SE_S_NW,
    SW_NW,
    N_SW_NW,
    NE_SW_NW,
    N_NE_SW_NW,
    SE_SW_NW,
    N_SE_SW_NW,
    NE_SE_SW_NW,
    N_NE_SE_SW_NW,
    S_SW_NW,
    N_S_SW_NW,
    NE_S_SW_NW,
    N_NE_S_SW_NW,
    SE_S_SW_NW,
    N_SE_S_SW_NW,
    NE_SE_S_SW_NW,
    N_NE_SE_S_SW_NW,
    C;

    public String value() {
        return name();
    }

    public static MultiDirection fromValue(String v) {
        return valueOf(v);
    }

}
