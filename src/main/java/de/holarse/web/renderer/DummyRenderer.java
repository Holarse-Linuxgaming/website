package de.holarse.web.renderer;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DummyRenderer implements Renderer {

    @Override
    public String render(final String content, final Map<String, String> context) {
        return content;
    }
    
}
