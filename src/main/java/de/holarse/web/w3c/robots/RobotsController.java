package de.holarse.web.w3c.robots;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Steuerung der Web-Crawler, nach https://www.robotstxt.org/
 */
@Controller
public class RobotsController {

    private static String CONTENT = "User-Agent: *\\n" +
                                    "Disallow: /search\\n" +
                                    "Disallow: /webapi\\n" +
                                    "Disallow: /api\\n" +
                                    "Sitemap: https://www.holarse-linuxgaming.de/sitemap.xml";


    @GetMapping("/robots.txt")
    public @ResponseBody String robotsTxt() {
        return CONTENT;
    }

}