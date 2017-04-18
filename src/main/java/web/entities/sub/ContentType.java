package web.entities.sub;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum(String.class)
public enum ContentType implements Serializable  {
    PLAIN,
    HTML,
    MARKDOWN,
    WIKI;

}
