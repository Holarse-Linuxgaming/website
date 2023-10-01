package de.holarse.web.controller;

import de.holarse.web.defines.WebDefines;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/search")
public class SearchController {
    
    @PostMapping
    public ModelAndView searchForm(@RequestParam("q") final String q) throws UnsupportedEncodingException {
        return new ModelAndView(String.format("redirect:/search?q=%s", q)); //URLEncoder.encode(q, StandardCharsets.UTF_8.toString())));
    }
    
    @GetMapping
    public ModelAndView search(@RequestParam("q") final String q, final ModelAndView mv) throws UnsupportedEncodingException {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/search/results");

        mv.addObject("q", q); //URLDecoder.decode(q, StandardCharsets.UTF_8.toString()));
        
        return mv;
    }
    
}
