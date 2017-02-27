package web.entities.sub;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum(String.class)
public enum TitleType {
    MAIN,
    ALTERNATIVE;
}
