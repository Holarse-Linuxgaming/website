package de.holarse.renderer.plain;

import de.holarse.renderer.Renderer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("plain")
public class PlainRenderer implements Renderer  {

    @Override
    public String render(final String source) {
        if (StringUtils.isBlank(source)) {
            return "";
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
