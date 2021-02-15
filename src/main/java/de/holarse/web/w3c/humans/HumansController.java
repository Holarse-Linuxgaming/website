package de.holarse.web.w3c.humans;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Implementation der Humans.txt nach http://humanstxt.org/DE
 */
@Controller
public class HumansController {

    private final static String CONTENT = "/* TEAM */\r\n" +
    "Site: contact@holarse-linuxgaming.de\r\n" +
    "Twitter: https://twitter.com/holarse\r\n" +
    "Youtube: https://www.youtube.com/user/holarse\r\n" + 
    "Mastodon: https://mastodon.social/@holarse\r\n" +
    "Discord: https://discord.io/holarse\r\n" +
    "Twitch: https://www.twitch.tv/holarse\r\n" +
    "Steam Curator: http://store.steampowered.com/curator/4317648-Holarse-Linuxgaming\r\n";

    @GetMapping("/humans.txt")
    public @ResponseBody String humansTxt() {
        return CONTENT;
    }

}