package de.holarse.web.controller;

import de.holarse.web.defines.WebDefines;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticController {

    @GetMapping("/datenschutz")
    public ModelAndView datenschutz(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/static/datenschutz");

        return mv;
    }

    @GetMapping("/community-regeln")
    public ModelAndView community_regeln(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/static/community-regeln");

        return mv;
    }

    @GetMapping("/impressum")
    public ModelAndView impressum(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/static/impressum");

        return mv;
    }

    @GetMapping("/help/wiki-syntax")
    public ModelAndView wikiSyntax(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/static/wiki-syntax");

        return mv;
    }

}
