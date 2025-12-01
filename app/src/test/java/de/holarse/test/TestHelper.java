package de.holarse.test;

import de.holarse.web.defines.WebDefines;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.ui.ModelMap;

public class TestHelper {

    public static String getContentView(final ResultActions result) throws Exception {
        final ModelMap map = result.andReturn().getModelAndView().getModelMap();
        if (map == null) {
            throw new RuntimeException("null map");
        }
        return map.getAttribute(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME).toString();
    }
    
}
