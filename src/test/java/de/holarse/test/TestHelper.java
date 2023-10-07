package de.holarse.test;

import de.holarse.web.defines.WebDefines;
import org.springframework.test.web.servlet.ResultActions;

public class TestHelper {

    public static String getContentView(final ResultActions result) throws Exception {
        return result.andReturn().getModelAndView().getModelMap().getAttribute(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME).toString();
    }
    
}
