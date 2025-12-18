package de.holarse.web.renderer.dummy;

import java.util.Map;

import de.holarse.web.renderer.ContentRenderer;
import org.springframework.stereotype.Component;

@Component
public class DummyRenderer implements ContentRenderer {

    @Override
    public String render(final String content, final Map<String, String> context) {
        return content;
    }
    
}
