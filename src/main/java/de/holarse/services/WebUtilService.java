package de.holarse.services;

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

    public String extractYoutubeId(final String youtubeUrl) throws MalformedURLException, URISyntaxException {
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
    
}
