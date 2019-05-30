package de.holarse.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class WebUtilService {
   
    protected String extractYoutubeId(final String youtubeUrl) throws MalformedURLException, URISyntaxException {
        if (StringUtils.isBlank(youtubeUrl)) {
            return null;
        }
        
       
        final URI uri = new URL(youtubeUrl).toURI();
        System.out.println("host:  " + uri.getHost());
        System.out.println("query: " + uri.getQuery());
        switch (uri.getHost()) {
            case "www.youtube.com":
            case "www.youtube-nocookie.com":
                // Nach v-Parameter prüfen
                for (final String param : uri.getQuery().split("&")) {
                    System.out.println("param: " + param);
                    if (param.toLowerCase().startsWith("v=")) {
                        final String[] value = param.split("=");
                        if (value.length == 2) {
                            return value[1];
                        }
                    }
                }
                break;
            case "youtu.be":
                return uri.getPath().substring(1);
            default:
                throw new MalformedURLException("Not a (valid) youtube url");
        }

        return null;
    }
    
    protected String buildYoutubeUrl(final String youtubeId) {
        return "//www.youtube-nocookie.com/embed/" + youtubeId;
    }
    
    protected String buildYoutubeThumbnail(final String youtubeId, final YoutubeThumbnailType type) {
        return "//i3.ytimg.com/vi/" + youtubeId + "/" + type.getFilename();
    }
    
    protected enum YoutubeThumbnailType {
        DEFAULT         ("default.jpg", "Default thumbnail"),
        ZERO            ("0.jpg", "full size image"),
        ONE             ("1.jpg", "thumbnail 1"),
        TWO             ("2.jpg", "thumbnail 2"),
        THREE           ("3.jpg", "thumbnail 3"),
        HQDEFAULT       ("hqdefault.jpg", "HQ default thumbnail"),
        MQDEFAULT       ("mqdefault.jpg", "Medium default thumbnail"),
        SDDEFALT        ("sddefault.jpg", "Standard definition default thumbnail"),
        MAXRESDEFAULT   ("maxresdefault.jpg", "Max resolution default thumbnail");
        
        private final String filename;
        private final String description;
        
        YoutubeThumbnailType(final String filename, final String description) {
            this.filename = filename;
            this.description = description;
        }
        
        public String getFilename() {
            return filename;
        }
        
        public String getDescription() {
            return description;
        }
    };

    /**
     * Erstellt die Youtube-Thumbnail-URL
     * @param youtubeId
     * @return
     * @throws Exception 
     */
    protected String getYoutubeThumbnail(final String youtubeId) throws Exception {
        return buildYoutubeThumbnail(youtubeId, YoutubeThumbnailType.DEFAULT);
    }
    
}
