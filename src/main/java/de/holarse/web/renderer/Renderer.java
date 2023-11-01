package de.holarse.web.renderer;

import java.util.Map;

public interface Renderer {

    String render(String content, Map<String, String> context);
    
}
