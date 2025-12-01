package de.holarse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class RedirectController {

    @GetMapping("/category/{something}/{slug}")
    public ModelAndView legacyTag1(@PathVariable("something") final String something, @PathVariable("slug") final String slug) {
            final UriComponents uriComponents = UriComponentsBuilder.newInstance().path("spielefinder")
                                                                                  .queryParam("t", slug).encode()
                                                                                  .queryParam("c", "0")
                                                                                  .build();
            
            return new ModelAndView(String.format("redirect:/%s", uriComponents.toUriString()));
    }    
    
}
