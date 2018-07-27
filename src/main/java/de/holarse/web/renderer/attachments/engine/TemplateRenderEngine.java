package de.holarse.web.renderer.attachments.engine;

import java.util.Map;

public interface TemplateRenderEngine {

    String execute(String template, Map<String, Object> properties) throws Exception;
    
}
