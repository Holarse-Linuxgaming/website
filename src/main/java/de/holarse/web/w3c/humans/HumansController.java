package de.holarse.web.w3c.humans;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Implementation der Humans.txt nach http://humanstxt.org/DE
 */
@Controller
public class HumansController {

    private static String CONTENT = "/* TEAM */\\n" +
    "Site: contact@holarse-linuxgaming.de\\n" +
    "Twitter: https://twitter.com/holarse\\n" +
    "Youtube: https://www.youtube.com/user/holarse\\n" + 
    "Mastodon: https://mastodon.social/@holarse\\n" +
    "Discord: https://discord.io/holarse\\n" +
    "Twitch: https://www.twitch.tv/holarse\\n" +
    "Steam Curator: http://store.steampowered.com/curator/4317648-Holarse-Linuxgaming\\n";

    @GetMapping("/humans.txt")
    public @ResponseBody String humansTxt() {
        return CONTENT;
    }

}