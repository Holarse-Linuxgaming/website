package de.holarse.services;

import de.holarse.web.videonews.YoutubeVideo;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.stereotype.Service;

@Service
public class WebUtilService {

    public YoutubeVideo parseYoutubeLink(final String link) throws Exception {
        final String youtubeId = extractYoutubeId(link);
        
        final YoutubeVideo video = new YoutubeVideo();
        video.setUrl(link);
        video.setYoutubeId(youtubeId);
        video.setYoutubeUrl(buildYoutubeUrl(youtubeId));
        video.setThumbnail(getYoutubeThumbnail(youtubeId));
        
        return video;
    }
    
    protected String extractYoutubeId(final String youtubeUrl) throws MalformedURLException, URISyntaxException {
        if (StringUtils.isBlank(youtubeUrl)) {
            return null;
        }
        
        final URI uri = new URL(youtubeUrl).toURI();
        System.out.println(uri.getHost());
        switch (uri.getHost()) {
            case "www.youtube.com":
            case "www.youtube-nocookie.com":
                // Nach v-Parameter prüfen
                final Optional<NameValuePair> nvp = URLEncodedUtils.parse(uri, "UTF-8").stream().filter(nv -> nv.getName().equalsIgnoreCase("v")).findFirst();
                if (nvp.isPresent()) {
                    return nvp.get().getValue();
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
