package de.holarse.web.renderer;

import java.util.Map;

public interface ContentRenderer {

    String render(String template, Map<String, String> kv);
}
